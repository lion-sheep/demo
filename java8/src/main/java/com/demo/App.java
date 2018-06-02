package com.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Student s = new Student("1","好运",new Date());
        String json = JSON.toJSONString(s);
        JSONObject o = new JSONObject();
    }
}
