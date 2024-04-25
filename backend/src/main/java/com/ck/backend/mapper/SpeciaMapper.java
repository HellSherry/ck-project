package com.ck.backend.mapper;

import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.Specia;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * (Specia)表数据库访问层
 *
 * @author hellSherry
 * @since 2023-12-29 11:26:14
 */
@Mapper
public interface SpeciaMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Specia queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param specia   查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Specia> queryAllByLimit(Specia specia, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param specia 查询条件
     * @return 总行数
     */
    Integer count(String search);

    /**
     * 新增数据
     *
     * @param specia 实例对象
     * @return 影响行数
     */
    int insert(Specia specia);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Specia> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Specia> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Specia> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Specia> entities);

    /**
     * 修改数据
     *
     * @param specia 实例对象
     * @return 影响行数
     */
    int update(Specia specia);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Specia> getAll();

    List<Specia> getAllFull();

    Integer deletes(String ids);

    List<Specia> selectLimit(Integer page, String search);
}

