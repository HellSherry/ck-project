package com.ck.backend.service.impl;


import com.ck.backend.mapper.UserMapper;
import com.ck.backend.pojo.User;
import com.ck.backend.service.RegistService;
import com.ck.backend.util.JwtUtil;
import com.ck.backend.util.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author HellSherrry
 * &#064;date 2023/12/17-13:30
 * @version 1.0
 * &#064;description
 */
@Service
public class RegistServiceImpl implements RegistService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Override
    public ResponseEntity regist(User user) {

        //如果没有通过给出提示
        if (Objects.isNull(user)) {
            return ResponseEntity.error("用户名或密码错误");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userMapper.regist(user) ==0) {
            return ResponseEntity.error("注册失败");
        }
        // 如果通过了，使用userid生成一个jwt
        user.setPassword(null);
        ObjectMapper mapper = new ObjectMapper();
        String userJson;
        try {
            userJson = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String jwt = JwtUtil.generateToken(userJson);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        System.out.println("map = " + map);
        //todo 将用户信息 存入redis userid作为key
        redisTemplate.opsForValue().set("login:" + user.getUserId(), jwt, 7, TimeUnit.DAYS);
//        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzAyODM2OTA2LCJpYXQiOjE3MDI4MDA5MDZ9.kjr9CksvIwkaiTMr_b7wbghx8JZfLEYV-x2FDsL77BY"
//        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1aWRcIjoxLFwicGhvbmVOdW1iZXJcIjpcIjEzMTMyODQ1MTJcIixcInBhc3N3b3JkXCI6bnVsbCxcInVuYW1lXCI6XCJyb290XCIsXCJnZW5kZXJcIjpcIuWls1wiLFwiZW1haWxcIjpudWxsLFwiaW5zZXJ0VGltZVwiOm51bGx9IiwiZXhwIjoxNzAyODM3NDY1LCJpYXQiOjE3MDI4MDE0NjV9.7LOJCYKNCKVb9_q-zrOzKeMcN_-fmnq49AOhfPTDuTk"
        return new ResponseEntity(map);
    }
}
