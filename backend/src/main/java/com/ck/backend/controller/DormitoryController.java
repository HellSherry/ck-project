package com.ck.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ck.backend.mapper.DormitoryMapper;
import com.ck.backend.pojo.Classroom;
import com.ck.backend.pojo.Dormitory;
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
@RequestMapping("/dormitory")
@RestController
@CrossOrigin
public class DormitoryController {
    @Resource
    private DormitoryMapper dormitoryMapper;
    @RequestMapping("/update")
    public ResponseEntity<Integer> update(@RequestBody Dormitory dormitory) {
        return ResponseEntity.ok(dormitoryMapper.updateById(dormitory));
    }
    @RequestMapping("/deletes")
    public ResponseEntity<Integer> delete(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok(dormitoryMapper.deleteBatchIds(ids));
    }
    @RequestMapping("/insert")
    public ResponseEntity<Integer> insert(@RequestBody Dormitory dormitory) {
        return ResponseEntity.ok( dormitoryMapper.insert(dormitory));
    }
    @GetMapping("/selectAll")
    public ResponseEntity<List<Dormitory>> selectAll(String names, Integer size , Integer page) {
        QueryWrapper<Dormitory> queryWrapper=new QueryWrapper<>();
        if(names!=null&&!names.equals("")){
            queryWrapper.like("name",names);
        }
        Page<Dormitory> page1=new Page<>(page,size);
        return ResponseEntity.ok(new PageBean<Dormitory>(null,null,Integer.valueOf(Math.toIntExact(dormitoryMapper.selectCount(null))),dormitoryMapper.selectPage(page1,queryWrapper).getRecords()));

    }
    @GetMapping("/count")
    public Long count() {
        return dormitoryMapper.selectCount(null);
    }
}
