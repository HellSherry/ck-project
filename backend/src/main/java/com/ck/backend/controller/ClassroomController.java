package com.ck.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.Classroom;
import com.ck.backend.service.ClassroomService;
import com.ck.backend.util.PageBean;
import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 小七
 * @version 1.0
 * @date 2024/3/14-14:12
 * @description
 */
@RestController
@RequestMapping("classroom")
@CrossOrigin
public class ClassroomController {
    @Resource
    private ClassroomService classroomService;

    @RequestMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody Classroom classroom) {
        return ResponseEntity.ok( classroomService.updateById(classroom));
    }
    @RequestMapping("/deletes")
    public ResponseEntity<Integer> delete(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok(classroomService.deleteBatchIds(ids));
    }
    @RequestMapping("/insert")
    public ResponseEntity<Integer> insert(@RequestBody Classroom classroom) {
        return ResponseEntity.ok(classroomService.insert(classroom));
    }
    @GetMapping("/selectAll")
    public ResponseEntity<PageBean<Classroom>> selectAll(String names, Integer size , Integer page) {
        QueryWrapper<Classroom> queryWrapper=new QueryWrapper<>();
        if(names!=null&&!names.equals("")){
            queryWrapper.like("name",names);
        }
        Page<Classroom> page1=new Page<>(page,size);
        return ResponseEntity.ok(new PageBean<Classroom>(null,null,Integer.valueOf(Math.toIntExact(classroomService.selectCount(null))),classroomService.selectPage(page1,queryWrapper).getRecords()));
    }

    @GetMapping("/count")
    public Long count() {
        return classroomService.selectCount(null);
    }
}
