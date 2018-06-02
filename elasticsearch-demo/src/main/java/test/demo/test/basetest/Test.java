package test.demo.test.basetest;

import test.demo.curd.RestfulCRUD;
import test.demo.curd.RestfulHttpClientCRUD;
import test.demo.curd.RestfulOKHttpClientCRUD;
import test.demo.po.Student;

/**
 * @author maguowei
 * @desc
 * @date 2018/4/18 下午3:34
 */
public class Test {
    public static void main001(String[] args) {
        RestfulCRUD clientCRUD = new RestfulHttpClientCRUD();
        Student s1 = new Student("1", "lion", 18, 1);
        clientCRUD.addIndex("school", "student", s1.getId(), "http://localhost", "9200", null, s1);
    }

    public static void main002(String[] args) {
        RestfulCRUD clientCRUD = new RestfulOKHttpClientCRUD();
        Student s1 = new Student("1", "lion", 18, 1);
        clientCRUD.addIndex("school", "student", s1.getId(), "http://localhost", "9200", null, s1);
    }

    public static void main003(String[] args) {
        RestfulCRUD clientCRUD = new RestfulOKHttpClientCRUD();
        Student s1 = new Student("1", "sheep", 28, 2);
        clientCRUD.updateIndex("school", "student", s1.getId(), "http://localhost", "9200", "_update", s1);
    }

    public static void main004(String[] args) {
        RestfulCRUD clientCRUD = new RestfulOKHttpClientCRUD();
        Student s = (Student) clientCRUD.getObjectByESSearch("school", "student", "1", "http://localhost", "9200", null, Student.class);
        System.out.println(s);
    }

}
