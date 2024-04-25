package com.ck.backend.controller;

import com.ck.backend.mapper.QuestionMapper;
import com.ck.backend.pojo.Question;
import com.ck.backend.service.QuestionService;
import com.ck.backend.util.PageBean;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.ck.backend.util.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * (Question)表控制层
 *
 * @author hellSherry
 * @since 2023-12-28 14:44:49
 */
@RestController
@RequestMapping("question")
public class QuestionController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionMapper questionMapper;
    /**
     * 分页查询
     *
     * @param question    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
//    @GetMapping
//    public ResponseEntity<Page<Question>> queryByPage(Question question, PageRequest pageRequest) {
//        return ResponseEntity.ok(this.questionService.queryByPage(question, pageRequest));
//    }
    @RequestMapping("selectLimit")
    public ResponseEntity<PageBean<Question>> selectLimit(Integer page,String search,String groupids) {
        return ResponseEntity.ok( new PageBean<Question>( null,null,  questionMapper.count(  search,  groupids),  this.questionMapper.selectLimit(page,search,groupids)));
    }


    @RequestMapping("deletes")
    public ResponseEntity<Integer> deletes(String  ids) {
        return ResponseEntity.ok(this.questionService.deletes(ids));
    }
    @RequestMapping("insert")
    public ResponseEntity<Integer> insert(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.insert(question));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Question> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.questionService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param question 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Question> add(Question question) {
        return ResponseEntity.ok(this.questionService.insert(question));
    }

    /**
     * 编辑数据
     *
     * @param question 实体
     * @return 编辑结果
     */
    @RequestMapping("update")
    public ResponseEntity<Integer> update(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.update(question));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.questionService.deleteById(id));
    }

}

