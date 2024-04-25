package com.ck.backend.service;

import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.Specia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (Specia)表服务接口
 *
 * @author hellSherry
 * @since 2023-12-29 11:26:14
 */
public interface SpeciaService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Specia queryById(Integer id);

    /**
     * 分页查询
     *
     * @param specia      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Specia> queryByPage(Specia specia, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param specia 实例对象
     * @return 实例对象
     */
    Integer insert(Specia specia);

    /**
     * 修改数据
     *
     * @param specia 实例对象
     * @return 实例对象
     */
    Integer update(Specia specia);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Specia> getAll();

    List<Specia> getAllFull();

    Integer deletes(String ids);

    Integer count(String search);

    List<Specia> selectLimit(Integer page, String search);
}
