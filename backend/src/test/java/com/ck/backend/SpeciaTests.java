package com.ck.backend;

import com.ck.backend.mapper.SpeciaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpeciaTests {

    @Autowired
    public SpeciaMapper speciaMapper;
    @Test
    void contextLoads() {

        System.out.println("speciaMapper.getAll() = " + speciaMapper.getAll());

    }


}
