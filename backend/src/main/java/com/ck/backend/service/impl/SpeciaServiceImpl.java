package com.ck.backend.service.impl;

import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.Specia;
import com.ck.backend.mapper.SpeciaMapper;
import com.ck.backend.service.SpeciaService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * (Specia)表服务实现类
 *
 * @author hellSherry
 * @since 2023-12-29 11:26:14
 */
@Service("speciaService")
public class SpeciaServiceImpl implements SpeciaService {
    @Resource
    private SpeciaMapper speciaMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Specia queryById(Integer id) {
        return this.speciaMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param specia      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Specia> queryByPage(Specia specia, PageRequest pageRequest) {
//        long total = this.speciaMapper.count(specia);
        return new PageImpl<>(this.speciaMapper.queryAllByLimit(specia, pageRequest), pageRequest, 0);
    }

    /**
     * 新增数据
     *
     * @param specia 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(Specia specia) {
        return   this.speciaMapper.insert(specia);
     }

    /**
     * 修改数据
     *
     * @param specia 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(Specia specia) {
        return this.speciaMapper.update(specia);
     }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.speciaMapper.deleteById(id) > 0;
    }

    @Override
    public List<Specia> getAll() {
    return     this.speciaMapper.getAll();

    }

    @Override
    public List<Specia> getAllFull() {
        return     this.speciaMapper.getAllFull();
    }

    @Override
    public Integer deletes(String ids) {
        return speciaMapper.deletes(ids);
    }

    @Override
    public Integer count(String search) {
        return speciaMapper.count(search);

    }

    @Override
    public List<Specia> selectLimit(Integer page, String search) {
        return speciaMapper.selectLimit(page,search);

    }
}
