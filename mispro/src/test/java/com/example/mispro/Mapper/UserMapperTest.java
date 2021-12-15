package com.example.mispro.Mapper;

import com.example.mispro.Pojo.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    @Autowired
    UserMapper userMapper;
    @Test
    void getUserById() {
        User user = userMapper.getUserById(1);
        System.out.println(user);
    }

    @Test
    void insertUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getUserByName() {
    }
}