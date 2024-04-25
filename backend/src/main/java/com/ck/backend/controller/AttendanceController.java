package com.ck.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ck.backend.mapper.AttendanceMapper;
import com.ck.backend.pojo.Attendance;
import com.ck.backend.pojo.Leaverequest;
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
@RequestMapping("/attendance")
@RestController
@CrossOrigin
public class AttendanceController {
    @Resource
    private AttendanceMapper attendanceMapper;
    @RequestMapping("/selectAll")
    public ResponseEntity<PageBean<Attendance>> selectAll(String names, Integer size , Integer page) {
        QueryWrapper<Attendance> queryWrapper=new QueryWrapper<>();
        if (names!=null && !names.equals("")){
            queryWrapper.like("stedent",names);
        }
        Page<Attendance> page1=new Page<>(page,size);
        Page<Attendance> leaverequestPage = attendanceMapper.selectPage(new Page<Attendance>(page, size), queryWrapper);

        return ResponseEntity.ok(leaverequestPage);
    }
    @RequestMapping("/update")
    public ResponseEntity<Attendance> update(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceMapper.updateById(attendance));
    }
    @RequestMapping("/delete")
     public ResponseEntity<Attendance> delete(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok(attendanceMapper.deleteBatchIds(ids));
    }
    @RequestMapping("/insert")
    public ResponseEntity<Attendance> insert(@RequestBody Attendance attendance) {
        return ResponseEntity.ok( attendanceMapper.insert(attendance));
    }
    @GetMapping("/count")
    public Long count() {
        return attendanceMapper.selectCount(null);
    }
}
