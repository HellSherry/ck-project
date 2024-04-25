package com.ck.backend.service.impl;

import com.ck.backend.pojo.QuestionCourse;
import com.ck.backend.pojo.QuestionGroup;
import com.ck.backend.mapper.QuestionGroupMapper;
import com.ck.backend.service.QuestionGroupService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * (QuestionGroup)表服务实现类
 *
 * @author hellSherry
 * @since 2023-12-29 19:14:36
 */
@Service("questionGroupService")
public class QuestionGroupServiceImpl implements QuestionGroupService {
    @Resource
    private QuestionGroupMapper questionGroupMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QuestionGroup queryById(Integer id) {
        return this.questionGroupMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param questionGroup 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @Override
    public Page<QuestionGroup> queryByPage(QuestionGroup questionGroup, PageRequest pageRequest) {

        return new PageImpl<>(this.questionGroupMapper.queryAllByLimit(questionGroup, pageRequest), pageRequest, 0);
    }

    /**
     * 新增数据
     *
     * @param questionGroup 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(QuestionGroup questionGroup) {
        return this.questionGroupMapper.insert(questionGroup);
     }

    /**
     * 修改数据
     *
     * @param questionGroup 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(QuestionGroup questionGroup) {
        return    this.questionGroupMapper.update(questionGroup);
     }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.questionGroupMapper.deleteById(id) > 0;
    }

    @Override
    public Integer deletes(String ids) {
        return questionGroupMapper.deletes(ids);
    }

    @Override
    public List<QuestionGroup> selectLimit(Integer page, String search) {
        return questionGroupMapper.selectLimit(page,search);
    }

    @Override
    public Integer count(String search) {
        return questionGroupMapper.count(search);
    }



}
