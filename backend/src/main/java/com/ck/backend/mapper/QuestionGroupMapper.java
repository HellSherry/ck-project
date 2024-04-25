package com.ck.backend.mapper;

import com.ck.backend.pojo.QuestionGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * (QuestionGroup)表数据库访问层
 *
 * @author hellSherry
 * @since 2023-12-29 19:14:36
 */
@Mapper
public interface QuestionGroupMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionGroup queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param questionGroup 查询条件
     * @param pageable      分页对象
     * @return 对象列表
     */
    List<QuestionGroup> queryAllByLimit(QuestionGroup questionGroup, @Param("pageable") Pageable pageable);


    List<QuestionGroup> selectAllByCourseid(Integer id);
    /**
     * 统计总行数
     *
     * @param questionGroup 查询条件
     * @return 总行数
     */
 
    /**
     * 新增数据
     *
     * @param questionGroup 实例对象
     * @return 影响行数
     */
    int insert(QuestionGroup questionGroup);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QuestionGroup> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QuestionGroup> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QuestionGroup> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QuestionGroup> entities);

    /**
     * 修改数据
     *
     * @param questionGroup 实例对象
     * @return 影响行数
     */
    int update(QuestionGroup questionGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Integer deletes(String ids);

    List<QuestionGroup> selectLimit(Integer page, String search);

    Integer count(String search);
}

