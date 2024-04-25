package com.ck.backend.service;

import com.ck.backend.pojo.Question;
import com.ck.backend.util.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Question)表服务接口
 *
 * @author hellSherry
 * @since 2023-12-28 14:44:49
 */
public interface QuestionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Question queryById(Integer id);

    /**
     * 分页查询
     *
     * @param question    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Question> queryByPage(Question question, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param question 实例对象
     * @return 实例对象
     */
    Integer insert(Question question);

    /**
     * 修改数据
     *
     * @param question 实例对象
     * @return 实例对象
     */
    Integer update(Question question);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Integer deletes(String ids);
}
