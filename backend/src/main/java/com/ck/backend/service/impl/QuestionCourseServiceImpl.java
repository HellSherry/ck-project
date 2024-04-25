package com.ck.backend.service.impl;

import com.ck.backend.pojo.QuestionCourse;
import com.ck.backend.mapper.QuestionCourseMapper;
import com.ck.backend.pojo.QuestionGroup;
import com.ck.backend.service.QuestionCourseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * (QuestionCourse)表服务实现类
 *
 * @author hellSherry
 * @since 2023-12-29 19:15:08
 */
@Service("questionCourseService")
public class QuestionCourseServiceImpl implements QuestionCourseService {
    @Resource
    private QuestionCourseMapper questionCourseMapper;

    @Override
    public List<QuestionGroup> getCourseAndGroupBySpeciaid(Integer id) {
        return this.questionCourseMapper.getCourseAndGroupBySpeciaid(id) ;
    }

    @Override
    public List<QuestionGroup> getAllCourseAndGroup() {
        return this.questionCourseMapper.getAllCourseAndGroup() ;

    }

    @Override
    public Integer deletes(String ids) {
        return questionCourseMapper.deletes(ids);
    }

    @Override
    public List<QuestionCourse> selectLimit(Integer page, String search) {
        return questionCourseMapper.selectLimit(page,search);
    }

    @Override
    public Integer count(String search) {
        return questionCourseMapper.count(search);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QuestionCourse queryById(Integer id) {
        return this.questionCourseMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param questionCourse 筛选条件
     * @param pageRequest    分页对象
     * @return 查询结果
     */
    @Override
    public Page<QuestionCourse> queryByPage(QuestionCourse questionCourse, PageRequest pageRequest) {
//        long total = this.questionCourseMapper.count(questionCourse);
        return new PageImpl<>(this.questionCourseMapper.queryAllByLimit(questionCourse, pageRequest), pageRequest, 0);
    }

    /**
     * 新增数据
     *
     * @param questionCourse 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(QuestionCourse questionCourse) {
        return   this.questionCourseMapper.insert(questionCourse);
     }

    /**
     * 修改数据
     *
     * @param questionCourse 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(QuestionCourse questionCourse) {
        return  this.questionCourseMapper.update(questionCourse);
     }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.questionCourseMapper.deleteById(id) > 0;
    }
}
