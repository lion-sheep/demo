package test.demo.po;

import java.io.Serializable;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/18 下午3:27
 */
public class Student implements Serializable{
    private static final long serialVersionUID = -784662721008637225L;
    private String id;
    private String name;
    private int age;
    private int gender;
    private String desc;

    public Student() {
    }

    public Student(String id,String name, int age, int gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Student(String name, int age, int gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Student(String id, String name, int age, int gender,String desc) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.desc =desc;
    }

    public Student(String desc) {
        this.desc =desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", desc='" + desc + '\'' +
                '}';
    }

}
