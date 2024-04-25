package com.ck.backend;

import com.ck.backend.mapper.PaperMapper;
import com.ck.backend.service.QuestionCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaperServiceTests {

    @Autowired
    public PaperMapper paperMapper;
    @Test
    void contextLoads() {

        System.out.println("speciaMapper.getAll() = " + paperMapper.selectAllByLimit("小", 2, null,null));

    }


}
