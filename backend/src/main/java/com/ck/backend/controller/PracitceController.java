package com.ck.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.ck.backend.domain.LoginUser;
import com.ck.backend.mapper.PracticeAnswerMapper;
import com.ck.backend.mapper.PracticeMapper;
import com.ck.backend.pojo.Practice;
import com.ck.backend.pojo.PracticeAnswer;
import com.ck.backend.pojo.Question;
import com.ck.backend.util.ResponseEntity;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * (practice)表控制层
 *
 * @author hellSherry
 * @since 2024-01-10 13:14:19
 */
@RestController
@CrossOrigin("*")
@RequestMapping("practice")
public class PracitceController {
    /**
     * 服务对象
     */
    @Resource
    private PracticeMapper practiceMapper;
    @Resource
    private PracticeAnswerMapper practiceAnswerMapper;

    /**
     * 分页查询 倒序
     *
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页后的消息集合
     */
    @RequestMapping("page")
    public ResponseEntity<Page<Practice>> page(@AuthenticationPrincipal LoginUser customUser, Integer page, String search, Integer size) {
        QueryWrapper<Practice> wrapper = new QueryWrapper<>();

        if (StringUtils.hasText(search)) {
            wrapper.like("name", search);
        }
        wrapper.eq("userid", customUser.getUser().getUserId());
        wrapper.orderByDesc("id");
        return new ResponseEntity<>(practiceMapper.selectPage(new Page<Practice>(page, size), wrapper));
    }

    @RequestMapping("getAll")
    public ResponseEntity<List<Practice>> getAll() {
        return new ResponseEntity<>(practiceMapper.selectList(null));
    }

    @RequestMapping("delete")
    public ResponseEntity delete(@RequestBody List<Integer> ids) {
        System.out.println("ids = " + ids);
        QueryWrapper<Practice> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return new ResponseEntity<>(practiceMapper.delete(wrapper));
    }

    @RequestMapping("insert")
    public ResponseEntity insert(@RequestBody Practice practice) {
        return new ResponseEntity<>(practiceMapper.insert(practice));
    }

    @RequestMapping("update")
    public ResponseEntity update(@RequestBody Practice practice) {
        System.out.println("practice = " + practice);
        return new ResponseEntity<>(practiceMapper.updateById(practice));
    }

    //    ----------------------------------------------------------------------------------

    /**
     * 按章节顺序出题
     *
     * @param groupId 章节id
     * @return 查询结果
     */
    @GetMapping("inOrder")
    public ResponseEntity inOrder(@RequestParam("groupId") Integer groupId, @AuthenticationPrincipal LoginUser customUser) {
        return ResponseEntity.ok(SqlRunner.db().selectList("SELECT * FROM `question` where groupId = {0} and id not in (SELECT pa.questionId from  practice_answer pa , practice p  where p.userId ={1})  limit 20 ", groupId, customUser.getUser().getUserId()));
    }

    /**
     * 按章节随机出题
     *
     * @param groupId 章节id
     * @return 查询结果
     */
    @GetMapping("inRandom")
    public ResponseEntity inRandom(@RequestParam("groupId") Integer groupId) {
        return ResponseEntity.ok(SqlRunner.db().selectList("SELECT * FROM `question` where groupId = {0} ORDER BY RAND() limit 20 ", groupId));
    }

    /**
     * 按课程随机出题
     *
     * @param courseId 课程id
     * @return 查询结果
     */
    @GetMapping("inCourseId")
    public ResponseEntity inCourseId(@RequestParam("courseId") Integer courseId, Integer num) {
        return ResponseEntity.ok(SqlRunner.db().selectList("SELECT q.* FROM `question` q LEFT JOIN question_group qg ON q.groupId = qg.id  WHERE courseid = {0} ORDER BY RAND() limit {1} ", courseId, num));
    }

    /**
     * 再做一遍
     *
     * @param pricaticeid 做题id
     * @return 查询结果
     */
    @GetMapping("again")
    public ResponseEntity again(@RequestParam("pricaticeid") Integer pricaticeid) {
        return ResponseEntity.ok(SqlRunner.db().selectList("SELECT * FROM `question` WHERE id in (SELECT questionid FROM practice_answer WHERE practiceid = {0}) ", pricaticeid));
    }

    /**
     * 考试再做一遍
     *
     * @param paperid 试卷id
     * @return 查询结果
     */
    @GetMapping("againExam")
    public ResponseEntity againExam(@RequestParam("paperid") Integer paperid, @AuthenticationPrincipal LoginUser customUser) throws IOException {
        if (SqlRunner.db().selectObj("SELECT COUNT(1) from answer where userId={0} and paperid= {1}", customUser.getUser().getUserId(), paperid).toString().equals("0")) {
            return ResponseEntity.error("请先完成试卷");
        } else
            return ResponseEntity.ok(SqlRunner.db().selectList("SELECT * FROM `question` WHERE id in {0} ", new ObjectMapper().readValue(SqlRunner.db().selectObj("SELECT questions FROM `paper` WHERE paperid= {0}", paperid).toString(), List.class)));
    }

    /**
     * 提交做题
     *
     * @param practice 用户做题的信息
     * @param answers  用户做题的回答信息和题目id
     * @return 做题id
     *
     */
    @RequestMapping("submit")
    public ResponseEntity submit(@RequestBody Practice practice, String answers) {
        practiceMapper.insert(practice);
        for (String answer : answers.split("-")) {
            String[] split = answer.split("@");
 practiceAnswerMapper.insert(new PracticeAnswer(practice.getId(), Integer.parseInt(split[0]), split[1]));
        }
        return new ResponseEntity<>(practice.getId());
    }

    /**
     * 通过id获取用户做题详情
     * @param id 用户做题的id
     * @return 做题详情
     */
    @RequestMapping("getDetailsByID")
    public ResponseEntity getDetailsByID(Integer id) {
        return new ResponseEntity<>(SqlRunner.db().selectList("SELECT answer,answers,question,ranswer,parse FROM `practice_answer` p LEFT JOIN question q ON  p.questionid =q.id WHERE p.practiceid= {0}", id));
    }

    /**
     * 通过id获取用户做题信息
     *
     * @param id 用户做题的id
     * @return 做题信息
     */
    @RequestMapping("getInfoByID")
    public ResponseEntity getInfoByID(Integer id) {
        return new ResponseEntity<>(SqlRunner.db().selectOne("SELECT * FROM `practice` WHERE id= {0}", id));
    }

    /**
     * 通过id获取用户做题简单详情
     *
     * @param id 用户做题的id
     * @return 做题简单详情
     */
    @RequestMapping("getSimpleDetailsByID")
    public ResponseEntity getSimpleDetailsByID(Integer id) {
        return new ResponseEntity<>(SqlRunner.db().selectList("SELECT answer,questionid,ranswer FROM `practice_answer` p LEFT JOIN question q ON  p.questionid =q.id WHERE p.practiceid= {0}", id));
    }

    /**
     * 通过用户id获取用户当天做题简单详情
     *
     * @return 做题简单详情
     */
    @RequestMapping("getTodaySimpleDetails")
    public ResponseEntity getTodaySimpleDetails(@AuthenticationPrincipal LoginUser customUser) {
        return ResponseEntity.ok(SqlRunner.db().selectList("SELECT answer,questionid,ranswer FROM `practice_answer` pa LEFT JOIN question q ON  pa.questionid =q.id LEFT JOIN practice p on p.id=pa.practiceid WHERE to_days(starttime)=to_days(now()) and userId= {0}", customUser.getUser().getUserId()));
    }
    /**
     * 通过用户id获取用户做题进度
     * @return 每个章节的做题数量
     */
    @RequestMapping("getProgress")
    public ResponseEntity getProgress(@AuthenticationPrincipal LoginUser customUser) {
        return ResponseEntity.ok(SqlRunner.db().selectList("SELECT count(1) as total,count(b.id) as num ,a.groupId  from question a LEFT JOIN (SELECT *  from question WHERE question.id in (SELECT questionid from practice_answer LEFT JOIN practice ON practice.id=practice_answer.practiceid WHERE userId={0} )) b on a.id =b.id  GROUP BY a.groupId", customUser.getUser().getUserId()));
    }



}

