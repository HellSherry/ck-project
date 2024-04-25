package com.ck.backend.service;

import com.ck.backend.pojo.Paper;
import com.ck.backend.util.PageBean;

/**
 * (Paper)表服务接口
 *
 * @author hellSherry
 * @since 2023-12-28 14:27:36
 */
public interface PaperService {

    Long getPaperTime(String paperId);

    /**
     * 通过ID查询单条数据
     *
     * @param paperid 主键
     * @return 实例对象
     */
    Paper queryById(Integer paperid);

    /**
     * 分页查询
     *
     * @param paper       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */

    PageBean<Paper> queryByPage(String search, Integer page, String starttime, String endtime);

    /**
     * 新增数据
     *
     * @param paper 实例对象
     * @return 实例对象
     */
    Paper insert(Paper paper);

    /**
     * 修改数据
     *
     * @param paper 实例对象
     * @return 实例对象
     */
    Paper update(Paper paper);

    /**
     * 通过主键删除数据
     *
     * @param paperid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer paperid);

}
