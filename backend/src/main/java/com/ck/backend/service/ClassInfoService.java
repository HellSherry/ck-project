package com.ck.backend.service;

import com.ck.backend.pojo.ClassInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (ClassInfo)表服务接口
 *
 * @author hellSherry
 * @since 2023-12-29 11:17:14
 */
public interface ClassInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ClassInfo queryById(Integer id);

    List<ClassInfo> selectAllBySpeciaid(Integer id);

    /**
     * 分页查询
     *
     * @param classInfo   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<ClassInfo> queryByPage(ClassInfo classInfo, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param classInfo 实例对象
     * @return 实例对象
     */
    Integer insert(ClassInfo classInfo);

    /**
     * 修改数据
     *
     * @param classInfo 实例对象
     * @return 实例对象
     */
    Integer update(ClassInfo classInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Integer count(String search);

    List<ClassInfo> selectLimit(Integer page, String search);

    Integer deletes(String ids);
}
