package com.example.sharding.mapper;

import com.example.sharding.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
	
	Long addUser(User user);
	
	List<User> list();

    List<User> findByName(String name);
}
