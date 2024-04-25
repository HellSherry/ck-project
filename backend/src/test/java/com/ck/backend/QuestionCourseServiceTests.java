package com.ck.backend;

import com.ck.backend.mapper.SpeciaMapper;
import com.ck.backend.service.QuestionCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionCourseServiceTests {

    @Autowired
    public QuestionCourseService questionCourseService;
    @Test
    void contextLoads() {

        System.out.println("speciaMapper.getAll() = " + questionCourseService.getCourseAndGroupBySpeciaid(1));

    }


}
