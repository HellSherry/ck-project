package com.ck.backend.controller;

import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.QuestionCourse;
import com.ck.backend.pojo.QuestionGroup;
import com.ck.backend.service.QuestionCourseService;
import com.ck.backend.util.PageBean;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.ck.backend.util.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (QuestionCourse)表控制层
 *
 * @author hellSherry
 * @since 2023-12-29 19:15:07
 */
@RestController
@RequestMapping("questionCourse")
public class QuestionCourseController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionCourseService questionCourseService;
    @RequestMapping("/selectLimit")
    public com.ck.backend.util.ResponseEntity<PageBean<QuestionGroup>> selectLimit(Integer page, String search) {
        return com.ck.backend.util.ResponseEntity.ok(new PageBean<QuestionCourse>(null,null,questionCourseService.count(search),questionCourseService.selectLimit(page,search))) ;
    }
    @RequestMapping("/getAll")
    public com.ck.backend.util.ResponseEntity<List<QuestionCourse>> getAll( ) {
        return com.ck.backend.util.ResponseEntity.ok( questionCourseService.selectLimit(null,null)) ;
    }
    @RequestMapping("deletes")
    public com.ck.backend.util.ResponseEntity<Integer> deletes(String  ids) {
        return com.ck.backend.util.ResponseEntity.ok(this.questionCourseService.deletes(ids));
    }

    @RequestMapping("getCourseAndGroupBySpeciaid")
    public com.ck.backend.util.ResponseEntity<List<QuestionGroup>> getCourseAndGroupBySpeciaid(Integer id) {
        System.out.println(id);
        return com.ck.backend.util.ResponseEntity.ok(this.questionCourseService.getCourseAndGroupBySpeciaid( id));
    }
    @RequestMapping("getAllCourseAndGroup")
    public com.ck.backend.util.ResponseEntity<List<QuestionGroup>> getAllCourseAndGroup( ) {

        return com.ck.backend.util.ResponseEntity.ok(this.questionCourseService.getAllCourseAndGroup( ));
    }
    /**
     * 分页查询
     *
     * @param questionCourse 筛选条件
     * @param pageRequest    分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<QuestionCourse>> queryByPage(QuestionCourse questionCourse, PageRequest pageRequest) {
        return ResponseEntity.ok(this.questionCourseService.queryByPage(questionCourse, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<QuestionCourse> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.questionCourseService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param questionCourse 实体
     * @return 新增结果
     */
    @RequestMapping("insert")
    public ResponseEntity<Integer> add(@RequestBody QuestionCourse questionCourse) {
        return ResponseEntity.ok(this.questionCourseService.insert(questionCourse));
    }

    /**
     * 编辑数据
     *
     * @param questionCourse 实体
     * @return 编辑结果
     */
    @RequestMapping("update")
    public ResponseEntity<Integer> edit(@RequestBody  QuestionCourse questionCourse) {
        return ResponseEntity.ok(this.questionCourseService.update(questionCourse));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.questionCourseService.deleteById(id));
    }

}

