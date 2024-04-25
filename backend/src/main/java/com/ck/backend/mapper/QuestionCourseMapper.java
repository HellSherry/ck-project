package com.ck.backend.mapper;

import com.ck.backend.pojo.QuestionCourse;
import com.ck.backend.pojo.QuestionGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * (QuestionCourse)表数据库访问层
 *
 * @author hellSherry
 * @since 2023-12-29 19:15:07
 */
@Mapper
public interface QuestionCourseMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionCourse queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param questionCourse 查询条件
     * @param pageable       分页对象
     * @return 对象列表
     */
    List<QuestionCourse> queryAllByLimit(QuestionCourse questionCourse, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param questionCourse 查询条件
     * @return 总行数
     */

    /**
     * 新增数据
     *
     * @param questionCourse 实例对象
     * @return 影响行数
     */
    int insert(QuestionCourse questionCourse);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QuestionCourse> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QuestionCourse> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QuestionCourse> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QuestionCourse> entities);

    /**
     * 修改数据
     *
     * @param questionCourse 实例对象
     * @return 影响行数
     */
    int update(QuestionCourse questionCourse);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<QuestionGroup> getCourseAndGroupBySpeciaid(Integer id);

    List<QuestionGroup> getAllCourseAndGroup();

    Integer deletes(String ids);

    List<QuestionCourse> selectLimit(Integer page, String search);

    Integer count(String search);
}

