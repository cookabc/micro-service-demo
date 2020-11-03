package com.example.basicDB;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Student
 *
 * @author: Xugang Song
 * @create: 2020/11/2
 */
public class Student implements Serializable {

    String name;

    int age;

    double score;

    public Student() {
    }

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

    @Override
    public String toString() {
        return "name: " + this.name + ", " +
                "age: " + this.age + ", " +
                "score: " + this.score;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Student> map = new HashMap<>();
        map.put("first", new Student("QQ", 13, 88.6));
        saveStudents(map);

        // serialization example
        Student student = new Student("张三", 18, 80.9d);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String str = mapper.writeValueAsString(student);
        System.out.println(str);

        // xml example
        mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        str = mapper.writeValueAsString(student);
        System.out.println(str);
        mapper.writeValue(new File("student.xml"), student);

        // MessagePack example
        mapper = new ObjectMapper(new MessagePackFactory());
        mapper.writeValue(new File("student.bson"), student);
        Student s = mapper.readValue(new File("student.bson"), Student.class);
        System.out.println(s.toString());

        // List example
        List<Student> students = Arrays.asList(
                new Student("张三", 18, 80.9d),
                new Student("李四", 17, 67.5d));
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        str = mapper.writeValueAsString(students);
        mapper.writeValue(new File("students.json"), students);
        System.out.println(str);

        List<Student> list = mapper.readValue(
                new File("students.json"),
                new TypeReference<>() {
                });
        System.out.println(list.toString());

        // Map example
        map = new HashMap<>();
        map.put("zhangsan", new Student("张三", 18, 80.9d));
        map.put("lisi", new Student("李四", 17, 67.5d));
        mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        str = mapper.writeValueAsString(map);
        mapper.writeValue(new File("students_map.xml"), map);
        System.out.println(str);

        mapper = new XmlMapper();
        map = mapper.readValue(
                new File("students_map.xml"),
                new TypeReference<>() {
                });
        System.out.println(map.toString());

        // complex object
        ComplexStudent complexStudent = new ComplexStudent("张三", 18);
        Map<String, Double> scoreMap = new HashMap<>();
        scoreMap.put("语文", 89d);
        scoreMap.put("数学", 83d);
        complexStudent.setScores(scoreMap);
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPhone("18500308990");
        contactInfo.setEmail("zhangsan@sina.com");
        contactInfo.setAddress("中关村");
        complexStudent.setContactInfo(contactInfo);

        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("complex_students.json"), complexStudent);

        ComplexStudent stu = mapper.readValue(new File("complex_students.json"), ComplexStudent.class);
        System.out.println(stu.toString());
        mapper.writeValue(System.out, stu);
    }

    static class ComplexStudent {
        String name;
        int age;
        Map<String, Double> scores;
        ContactInfo contactInfo;

        public ComplexStudent() {
        }

        public ComplexStudent(String name, int age) {
            this.name = name;
            this.age = age;
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

        public Map<String, Double> getScores() {
            return scores;
        }

        public void setScores(Map<String, Double> scores) {
            this.scores = scores;
        }

        public ContactInfo getContactInfo() {
            return contactInfo;
        }

        public void setContactInfo(ContactInfo contactInfo) {
            this.contactInfo = contactInfo;
        }
    }

    static class ContactInfo {
        String phone;
        String address;
        String email;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
