package com.ck.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ck.backend.pojo.Practice;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Practice)表数据库访问层
 *
 * @author hellSherry
 * @since 2024-01-10 13:14:19
 */
@Mapper
public interface PracticeMapper extends BaseMapper<Practice> {
}

