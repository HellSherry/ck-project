package com.ck.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ck.backend.mapper.LeaverequestMapper;
import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.Leaverequest;
import com.ck.backend.util.PageBean;
import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 小七
 * @version 1.0
 * @date 2024/2/27-14:18
 * @description
 */
@RequestMapping("/leaverequest")
@RestController
@CrossOrigin
public class LeaverequestController {
    @Resource
    private LeaverequestMapper leaverequestMapper;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @RequestMapping("/update")
    public ResponseEntity<Leaverequest> update(@RequestBody Leaverequest leaverequest) {
        return ResponseEntity.ok(leaverequestMapper.updateById(leaverequest));
    }
    @RequestMapping("/delete")
    public ResponseEntity<Leaverequest> delete(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok( leaverequestMapper.deleteBatchIds(ids));
    }
    @RequestMapping("/insert")
    public ResponseEntity<Leaverequest> insert(@RequestBody Leaverequest leaverequest) {
        return ResponseEntity.ok(leaverequestMapper.insert(leaverequest));
    }
    @GetMapping("/selectAll")
    public ResponseEntity<PageBean<Leaverequest>> selectAll(String names, Integer size , Integer page) {
        QueryWrapper<Leaverequest> queryWrapper=new QueryWrapper<>();
        if(names!=null&&!names.equals("")){
            queryWrapper.like("status",names);
        }
        Page<Leaverequest> page1=new Page<>(page,size);
        Page<Leaverequest> leaverequestPage = leaverequestMapper.selectPage(new Page<Leaverequest>(page, size), queryWrapper);
        return ResponseEntity.ok(leaverequestPage);
    }
    @GetMapping("/count")
    public Long count() {
        return leaverequestMapper.selectCount(null);
    }
}
