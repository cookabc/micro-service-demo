package com.example.basicdb;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Student
 *
 * @author: Xugang Song
 * @create: 2020/11/2
 */
public class Student {

    String name;

    int age;

    double score;

    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public static void saveStudents(Map<String, Student> students)
            throws IOException {
        BasicDB db = new BasicDB("./", "students");
        for (Map.Entry<String, Student> kv : students.entrySet()) {
            db.put(kv.getKey(), toBytes(kv.getValue()));
        }
        db.close();
    }

    private static byte[] toBytes(Student student) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        dout.writeUTF(student.getName());
        dout.writeInt(student.getAge());
        dout.writeDouble(student.getScore());
        return bout.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        Map<String, Student> map = new HashMap<>();
        map.put("first", new Student("QQ", 13, 88.6));
        saveStudents(map);
    }
}
