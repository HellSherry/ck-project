package com.ck.backend.service.impl;

import com.ck.backend.pojo.Question;
import com.ck.backend.mapper.QuestionMapper;
import com.ck.backend.service.QuestionService;
import com.ck.backend.util.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import jakarta.annotation.Resource;

/**
 * (Question)表服务实现类
 *
 * @author hellSherry
 * @since 2023-12-28 14:44:49
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Question queryById(Integer id) {
        return this.questionMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param question    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Question> queryByPage(Question question, PageRequest pageRequest) {
        long total = this.questionMapper.count(null,null);
        return new PageImpl<>(this.questionMapper.queryAllByLimit(question, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param question 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(Question question) {
        return   this.questionMapper.insert(question);
     }

    /**
     * 修改数据
     *
     * @param question 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(Question question) {
        return    this.questionMapper.update(question) ;

    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.questionMapper.deleteById(id) > 0;
    }

    @Override
    public Integer deletes(String ids) {
        return this.questionMapper.deletes(ids) ;
    }
}
