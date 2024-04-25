package com.ck.backend.mapper;

import com.ck.backend.pojo.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * (Question)表数据库访问层
 *
 * @author hellSherry
 * @since 2023-12-28 14:44:49
 */
@Mapper
public interface QuestionMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Question queryById(Integer id);

    List<Question> queryByGroup(    Integer groupId,Integer num);
    /**
     * 查询指定行数据
     *
     * @param question 查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Question> queryAllByLimit(Question question, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param question 查询条件
     * @return 总行数
     */
    int count(String search,String groupids);

    /**
     * 新增数据
     *
     * @param question 实例对象
     * @return 影响行数
     */
    int insert(Question question);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Question> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Question> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Question> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Question> entities);

    /**
     * 修改数据
     *
     * @param question 实例对象
     * @return 影响行数
     */
    int update(Question question);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Question> selectLimit(Integer page,String search,String groupids);

    Integer deletes(@Param("ids") String ids);
}

