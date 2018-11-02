package com.demo;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xiaoma
 * @desc
 * @date 2018/10/22 下午2:27
 */
public class AppTest implements Serializable {

    @Test
    public void test() {
        Logger log = Logger.getLogger(AppTest.class);
        HashSet<Integer> set = new HashSet() {{
            add(1);
            add(2);
            add(3);
        }};
        System.out.println(set.contains(1));
        log.info(set.contains(1));
        log.debug(set.contains(1));
        log.warn(set.contains(1));
        log.error(set.contains(1));
        log.trace("1111111111111111");
        log.fatal(set.contains(1));
    }

    class Student implements Serializable {
        private String name;
        private int age;
        private LocalDateTime birthday;

        public Student() {
        }

        public Student(String name, int age, LocalDateTime birthday) {
            this.name = name;
            this.age = age;
            this.birthday = birthday;
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

        public LocalDateTime getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDateTime birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", birthday=" + birthday +
                    '}';
        }
    }

    @Test
    public void test2() throws Exception {
        LinkedBlockingQueue lbq = new LinkedBlockingQueue();
        Student s1 = new Student("小马哥", 30, LocalDateTime.now());
        lbq.add(s1);
        System.out.println("3333333");
        Object o = lbq.take();
        System.out.println(o);
        Object oo = new Object();
        lbq.add(oo);
        Object oooo = lbq.take();
        if (oooo == oo) {
            System.out.println("111111");
        }
    }

    @Test
    public void serialize() throws Exception {
        Logger log = Logger.getLogger(AppTest.class);
        Student s1 = new Student("小马哥", 30, LocalDateTime.now());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(s1);
        System.out.println(baos.toByteArray());
        byte[] bytes = baos.toByteArray();
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
        }
        System.out.println();
        log.info("==========================================");
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student s = (Student) ois.readObject();
        System.out.println(s);
    }
}
