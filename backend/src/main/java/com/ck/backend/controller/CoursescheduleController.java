package com.ck.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ck.backend.controller.websokect.SpringWebSokectController;
import com.ck.backend.mapper.CoursescheduleMapper;
 import com.ck.backend.pojo.Courseschedule;
 import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * (courseschedule)表控制层
 *
 * @author hellSherry
 * @since 2024-01-10 13:14:19
 */
@RestController
@CrossOrigin("*")
@RequestMapping("courseschedule")
public class CoursescheduleController {
    /**
     * 服务对象
     */
    @Resource
    private CoursescheduleMapper coursescheduleMapper;

    /**
     * 分页查询消息
     *
     * @param search 搜索关键字
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页后的消息集合
     */
    @RequestMapping("page")
    public ResponseEntity<Page<Courseschedule>> page(String search, Integer page, Integer size) {
        QueryWrapper<Courseschedule> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(search)) {
            wrapper.like("title", search).or(
                    wt-> wt.like("details", search)
            );

        }
        Page<Courseschedule> BookbagMapperPage = coursescheduleMapper.selectPage(new Page<Courseschedule>(page, size), wrapper);
        System.out.println("BookbagMapperPage.getRecords() = " + BookbagMapperPage.getRecords());
        return new ResponseEntity<>(BookbagMapperPage);
    }
    @RequestMapping("getByClassId")
    public ResponseEntity<List<Courseschedule>> getByClassId( Integer classid) {
        QueryWrapper<Courseschedule> wrapper = new QueryWrapper<>();

            wrapper.eq("classid", classid) ;


         return new ResponseEntity<>(  coursescheduleMapper.selectList(  wrapper));
    }

    @RequestMapping("getAll")
    public ResponseEntity<List<Courseschedule>> getAll() {
        return new ResponseEntity<>(coursescheduleMapper.selectList(null));
    }
    @RequestMapping("delete")
    public ResponseEntity delete(@RequestBody List<Integer> ids) {
        System.out.println("ids = " + ids);
        QueryWrapper<Courseschedule> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return new ResponseEntity<>(coursescheduleMapper.delete(wrapper));
    }
    @RequestMapping("insert")
    public ResponseEntity insert(@RequestBody Courseschedule courseschedule) {
         return new ResponseEntity<>(coursescheduleMapper.insert(courseschedule));
    }
    @RequestMapping("update")
    public ResponseEntity update(@RequestBody Courseschedule courseschedule) {
        System.out.println("courseschedule = " + courseschedule);
        return new ResponseEntity<>(coursescheduleMapper.updateById(courseschedule));
    }

}

