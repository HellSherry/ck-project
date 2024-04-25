package com.ck.backend.controller;

import com.ck.backend.pojo.QuestionCourse;
import com.ck.backend.pojo.QuestionGroup;
import com.ck.backend.service.QuestionGroupService;
import com.ck.backend.util.PageBean;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.ck.backend.util.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (QuestionGroup)表控制层
 *
 * @author hellSherry
 * @since 2023-12-29 19:14:36
 */
@RestController
@RequestMapping("questionGroup")
public class QuestionGroupController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionGroupService questionGroupService;

    @RequestMapping("/selectLimit")
    public com.ck.backend.util.ResponseEntity<PageBean<QuestionGroup>> selectLimit(Integer page, String search) {
        return com.ck.backend.util.ResponseEntity.ok(new PageBean<QuestionGroup>(null,null,questionGroupService.count(search),questionGroupService.selectLimit(page,search))) ;
    }
    @RequestMapping("deletes")
    public com.ck.backend.util.ResponseEntity<Integer> deletes(String  ids) {
        return com.ck.backend.util.ResponseEntity.ok(this.questionGroupService.deletes(ids));
    }

    /**
     * 分页查询
     *
     * @param questionGroup 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<QuestionGroup>> queryByPage(QuestionGroup questionGroup, PageRequest pageRequest) {
        return ResponseEntity.ok(this.questionGroupService.queryByPage(questionGroup, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<QuestionGroup> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.questionGroupService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param questionGroup 实体
     * @return 新增结果
     */
    @RequestMapping("insert")
    public ResponseEntity<Integer> add(@RequestBody QuestionGroup questionGroup) {
        return ResponseEntity.ok(this.questionGroupService.insert(questionGroup));
    }

    /**
     * 编辑数据
     *
     * @param questionGroup 实体
     * @return 编辑结果
     */
    @RequestMapping("update")
    public ResponseEntity<Integer> edit(@RequestBody QuestionGroup questionGroup) {
        return ResponseEntity.ok(this.questionGroupService.update(questionGroup));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.questionGroupService.deleteById(id));
    }

}

