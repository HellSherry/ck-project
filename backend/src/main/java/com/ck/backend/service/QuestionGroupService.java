package com.ck.backend.service;

import com.ck.backend.pojo.QuestionGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (QuestionGroup)表服务接口
 *
 * @author hellSherry
 * @since 2023-12-29 19:14:36
 */
public interface QuestionGroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionGroup queryById(Integer id);

    /**
     * 分页查询
     *
     * @param questionGroup 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    Page<QuestionGroup> queryByPage(QuestionGroup questionGroup, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param questionGroup 实例对象
     * @return 实例对象
     */
    Integer insert(QuestionGroup questionGroup);

    /**
     * 修改数据
     *
     * @param questionGroup 实例对象
     * @return 实例对象
     */
    Integer update(QuestionGroup questionGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    Integer deletes(String ids);

    List<QuestionGroup> selectLimit(Integer page, String search);

    Integer count(String search);
}
