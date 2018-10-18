package com.example.test.mapper;

import com.example.test.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



@Mapper
public interface UserMapper {
	
	Long addUser(User user);
	
	List<User> list();
	
	User findById(Long id);

    List<User> findByName(String name);
}
