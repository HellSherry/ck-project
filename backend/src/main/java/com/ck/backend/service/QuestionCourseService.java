package com.ck.backend.service;

import com.ck.backend.pojo.QuestionCourse;
import com.ck.backend.pojo.QuestionGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (QuestionCourse)表服务接口
 *
 * @author hellSherry
 * @since 2023-12-29 19:15:07
 */
public interface QuestionCourseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionCourse queryById(Integer id);

    /**
     * 分页查询
     *
     * @param questionCourse 筛选条件
     * @param pageRequest    分页对象
     * @return 查询结果
     */
    Page<QuestionCourse> queryByPage(QuestionCourse questionCourse, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param questionCourse 实例对象
     * @return 实例对象
     */
    Integer insert(QuestionCourse questionCourse);

    /**
     * 修改数据
     *
     * @param questionCourse 实例对象
     * @return 实例对象
     */
    Integer update(QuestionCourse questionCourse);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<QuestionGroup> getCourseAndGroupBySpeciaid(Integer id);

    List<QuestionGroup> getAllCourseAndGroup();

    Integer deletes(String ids);

    List<QuestionCourse> selectLimit(Integer page, String search);

    Integer count(String search);
}
