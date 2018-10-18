package com.example.test;

import com.example.test.mapper.UserMapper;
import com.example.test.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void list() {
        List<User> users = userMapper.list();
        System.out.println(users);
    }

    @Test
    public void add() {
        for (long i = 0; i < 100; i++) {
            User user = new User();
            user.setId(i);
            user.setCity("深圳");
            user.setName("李四");
            userMapper.addUser(user);
        }
    }

    @Test
    public void getById() {
        User user = userMapper.findById(1L);
        System.out.println(user);
    }

    @Test
    public void getByName() {
        List<User> users = userMapper.findByName("李四");
        System.out.println(users);
    }

}
