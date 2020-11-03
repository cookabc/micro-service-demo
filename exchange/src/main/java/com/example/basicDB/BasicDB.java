package com.example.basicDB;

import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * BasicDB
 *
 * @author: Xugang Song
 * @create: 2020/11/2
 */
public class BasicDB {

    private static final int MAX_DATA_LENGTH = 1020;
    private static final byte[] ZERO_BYTES = new byte[MAX_DATA_LENGTH];
    private static final String DATA_SUFFIX = ".data";
    private static final String META_SUFFIX = ".meta";

    Map<String, Long> indexMap;
    Queue<Long> gaps;

    RandomAccessFile db;
    File metaFile;

    public BasicDB(String path, String name) throws IOException {
        File dataFile = new File(path + name + DATA_SUFFIX);
        metaFile = new File(path + name + META_SUFFIX);
        db = new RandomAccessFile(dataFile, "rw");
        if (metaFile.exists()) {
            loadMeta();
        } else {
            indexMap = new HashMap<>();
            gaps = new ArrayDeque<>();
        }
    }

//    public static void saveStudents(Map<String, Student> students) throws IOException {
//        BasicDB db = new BasicDB("./", "students");
//        for (Map.Entry<String, Student> kv : students.entrySet()) {
//            db.put(kv.getKey(), toBytes(kv.getValue()));
//        }
//        db.close();
//    }
//
//    private static byte[] toBytes(Student student) throws IOException {
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        DataOutputStream dout = new DataOutputStream(bout);
//        dout.writeUTF(student.getName());
//        dout.writeInt(student.getAge());
//        dout.writeDouble(student.getScore());
//        return bout.toByteArray();
//    }

    public void put(String key, byte[] value) throws IOException {
        Long index = indexMap.get(key);
        if (index == null) {
            index = nextAvailablePos();
            indexMap.put(key, index);
        }
        writeData(index, value);
    }

    private void writeData(Long pos, byte[] data) throws IOException {
        if (data.length > MAX_DATA_LENGTH) {
            throw new IllegalArgumentException("maximum allowed length is " + MAX_DATA_LENGTH +
                    ", data length is " + data.length);
        }
        db.seek(pos);
        db.writeInt(data.length);
        db.write(data);
        db.write(ZERO_BYTES, 0, MAX_DATA_LENGTH - data.length);
    }

    private Long nextAvailablePos() throws IOException {
        if (!gaps.isEmpty()) {
            return gaps.poll();
        } else {
            return db.length();
        }
    }

    public byte[] get(String key) throws IOException {
        Long index = indexMap.get(key);
        if (index != null) {
            return getData(index);
        }
        return null;
    }

    private byte[] getData(Long pos) throws IOException {
        db.seek(pos);
        int length = db.readInt();
        byte[] data = new byte[length];
        db.readFully(data);
        return data;
    }

    public void remove(String key) {
        Long index = indexMap.get(key);
        if (index != null) {
            gaps.offer(index);
        }
    }

    public void flush() throws IOException {
        saveMeta();
        db.getFD().sync();
    }

    private void saveMeta() throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(metaFile)))) {
            saveIndex(out);
            saveGaps(out);
        }
    }

    private void saveIndex(DataOutputStream out) throws IOException {
        out.writeInt(indexMap.size());
        for (Map.Entry<String, Long> entry : indexMap.entrySet()) {
            out.writeUTF(entry.getKey());
            out.writeLong(entry.getValue());
        }
    }

    private void saveGaps(DataOutputStream out) throws IOException {
        out.writeInt(gaps.size());
        for (Long pos : gaps) {
            out.writeLong(pos);
        }
    }

    private void loadMeta() throws IOException {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(metaFile)))) {
            loadIndex(in);
            loadGaps(in);
        }
    }

    private void loadIndex(DataInputStream in) throws IOException {
        int size = in.readInt();
        indexMap = new HashMap<>((int) (size / 0.75f) + 1, 0.75f);
        for (int i = 0; i < size; i++) {
            String key = in.readUTF();
            long index = in.readLong();
            indexMap.put(key, index);
        }
    }

    private void loadGaps(DataInputStream in) throws IOException {
        int size = in.readInt();
        gaps = new ArrayDeque<>(size);
        for (int i = 0; i < size; i++) {
            long index = in.readLong();
            gaps.add(index);
        }
    }

    public void close() throws IOException {
        flush();
        db.close();
    }
}
