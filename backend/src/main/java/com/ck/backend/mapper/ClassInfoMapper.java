package com.ck.backend.mapper;

import com.ck.backend.pojo.ClassInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * (ClassInfo)表数据库访问层
 *
 * @author hellSherry
 * @since 2023-12-29 11:17:14
 */
@Mapper
public interface ClassInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ClassInfo queryById(Integer id);


    List<ClassInfo> selectAllBySpeciaid(Integer specialid);

    /**
     * 查询指定行数据
     *
     * @param classInfo 查询条件
     * @param pageable  分页对象
     * @return 对象列表
     */
    List<ClassInfo> queryAllByLimit(ClassInfo classInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param classInfo 查询条件
     * @return 总行数
     */
    Integer count(String search);

    /**
     * 新增数据
     *
     * @param classInfo 实例对象
     * @return 影响行数
     */
    int insert(ClassInfo classInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ClassInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ClassInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ClassInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ClassInfo> entities);

    /**
     * 修改数据
     *
     * @param classInfo 实例对象
     * @return 影响行数
     */
    int update(ClassInfo classInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<ClassInfo> selectLimit(Integer page, String search);

    Integer deletes(@Param("ids") String ids);
}

