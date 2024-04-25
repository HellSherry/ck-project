package com.ck.backend.service.impl;

import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.mapper.ClassInfoMapper;
import com.ck.backend.service.ClassInfoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * (ClassInfo)表服务实现类
 *
 * @author hellSherry
 * @since 2023-12-29 11:17:14
 */
@Service("classInfoService")
public class ClassInfoServiceImpl implements ClassInfoService {
    @Resource
    private ClassInfoMapper classInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ClassInfo queryById(Integer id) {
        return this.classInfoMapper.queryById(id);
    }


    @Override
    public List<ClassInfo> selectAllBySpeciaid(Integer id) {
        return this.classInfoMapper.selectAllBySpeciaid(id);
    }

    /**
     * 分页查询
     *
     * @param classInfo   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<ClassInfo> queryByPage(ClassInfo classInfo, PageRequest pageRequest) {
//        long total = this.classInfoMapper.count(classInfo);
        return new PageImpl<>(this.classInfoMapper.queryAllByLimit(classInfo, pageRequest), pageRequest, 0);
    }

    /**
     * 新增数据
     *
     * @param classInfo 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(ClassInfo classInfo) {
        return   this.classInfoMapper.insert(classInfo);
     }

    /**
     * 修改数据
     *
     * @param classInfo 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(ClassInfo classInfo) {
        return     this.classInfoMapper.update(classInfo);
     }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.classInfoMapper.deleteById(id) > 0;
    }

    @Override
    public Integer count(String search) {
        return classInfoMapper.count(search);
    }

    @Override
    public List<ClassInfo> selectLimit(Integer page, String search) {
        return classInfoMapper.selectLimit(page,search);

    }

    @Override
    public Integer deletes(String ids) {
        return classInfoMapper.deletes(ids);
    }
}
