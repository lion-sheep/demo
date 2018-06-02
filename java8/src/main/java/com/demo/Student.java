package com.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author maguowei
 * @desc
 * @date 2018/5/7 上午12:16
 */
@Data
@AllArgsConstructor
public class Student {
    private String id;
    private String name;
    private Date birthday;

}
