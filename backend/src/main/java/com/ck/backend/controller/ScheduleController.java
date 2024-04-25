package com.ck.backend.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ck.backend.mapper.ScheduleMapper;
import com.ck.backend.pojo.Attendance;
import com.ck.backend.pojo.Schedule;
import com.ck.backend.util.PageBean;
import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author 小七
 * @version 1.0
 * @date 2024/2/27-14:18
 * @description
 */
@RequestMapping("/schedule")
@RestController
@CrossOrigin
public class ScheduleController {
    @Resource
    private ScheduleMapper scheduleMapper;
    @RequestMapping("/update")
    public ResponseEntity<Schedule> update(@RequestBody Schedule schedule) {
        return ResponseEntity.ok( scheduleMapper.updateById(schedule));
    }
    @RequestMapping("/delete")
    public ResponseEntity<Schedule> delete(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok( scheduleMapper.deleteBatchIds(ids));
    }
    @RequestMapping("/insert")
    public ResponseEntity<Schedule> insert(@RequestBody Schedule schedule) {
        return ResponseEntity.ok( scheduleMapper.insert(schedule));
    }
    @GetMapping("/selectAll")
    public ResponseEntity<PageBean<Schedule>> select() {
        Page<Schedule> leaverequestPage = scheduleMapper.selectPage(new Page<Schedule>(0, 20), null);
        return ResponseEntity.ok(leaverequestPage);
    }
}
