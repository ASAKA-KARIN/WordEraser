package com.example.mispro.Mapper;

import com.example.mispro.Pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
     User getUserById(Integer uid);
     void insertUser(User user);
     void updateUser(User user);
     User getUserByName(String userName);
}
