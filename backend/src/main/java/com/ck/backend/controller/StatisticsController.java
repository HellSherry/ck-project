package com.ck.backend.controller;

import com.ck.backend.controller.websokect.SpringWebSokectController;
import com.ck.backend.mapper.StatisticsMapper;
import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author HellSherrry
 * &#064;date 2023/12/25-19:55
 * @version 1.0
 * &#064;description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @RequestMapping(value = "classCount")
    public ResponseEntity classCount()  {
        return ResponseEntity.ok(statisticsMapper.getClassCountAsSpecil()) ;
    }
    @RequestMapping(value = "courseCount")
    public ResponseEntity courseCount()  {
        return ResponseEntity.ok(statisticsMapper.getCourseCountAsSpecil()) ;
    }
    @RequestMapping(value = "userCount")
    public ResponseEntity userCount()  {
        return ResponseEntity.ok(statisticsMapper.getUserCount()) ;
    }
    @RequestMapping(value = "more")
    public ResponseEntity more()  {

        ArrayList<HashMap> list = new ArrayList<>();

        HashMap<String, Object> objectObjectHashMap1 = new HashMap<>();
        objectObjectHashMap1.put("name", "课程数");
        objectObjectHashMap1.put("value", statisticsMapper.getCoutseCount());
        list.add(objectObjectHashMap1);
        HashMap<String, Object> objectObjectHashMap2 = new HashMap<>();
        objectObjectHashMap2.put("name", "问题数");
        objectObjectHashMap2.put("value", statisticsMapper.getQuestionCount());
        list.add(objectObjectHashMap2);
        HashMap<String, Object> objectObjectHashMap3 = new HashMap<>();
        objectObjectHashMap3.put("name", "学生考试数");
        objectObjectHashMap3.put("value", statisticsMapper.getAnswerCount());
        list.add(objectObjectHashMap3);
        HashMap<String, Object> objectObjectHashMap4 = new HashMap<>();
        objectObjectHashMap4.put("name", "消息数");
        objectObjectHashMap4.put("value", statisticsMapper.getMessageCount());
        list.add(objectObjectHashMap4);
        HashMap<String, Object> objectObjectHashMap5 = new HashMap<>();
        objectObjectHashMap5.put("name", "班级数");
        objectObjectHashMap5.put("value", statisticsMapper.getClassCount());
        list.add(objectObjectHashMap5);
        HashMap<String, Object> objectObjectHashMap6 = new HashMap<>();
        objectObjectHashMap6.put("name", "教师数");
        objectObjectHashMap6.put("value", statisticsMapper.getTeacherCount());
        list.add(objectObjectHashMap6);
        HashMap<String, Object> objectObjectHashMap7 = new HashMap<>();
        objectObjectHashMap7.put("name", "学生数");
        objectObjectHashMap7.put("value", statisticsMapper.getStudentCount());
        list.add(objectObjectHashMap7);
        HashMap<String, Object> objectObjectHashMap8 = new HashMap<>();
        objectObjectHashMap8.put("name", "试卷数");
        objectObjectHashMap8.put("value", statisticsMapper.getPaperCount());
        list.add(objectObjectHashMap8);
        HashMap<String, Object> objectObjectHashMap9 = new HashMap<>();
        objectObjectHashMap9.put("name", "在线人数");
        objectObjectHashMap9.put("value", SpringWebSokectController.getSessionPool().size());
        list.add(objectObjectHashMap9);

        return ResponseEntity.ok(list) ;
    }


}
