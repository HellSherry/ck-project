package com.ck.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ck.backend.pojo.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * (Message)表数据库访问层
 *
 * @author hellSherry
 * @since 2024-01-10 13:14:19
 */
@Mapper
public interface MessageMapper  extends BaseMapper<Message> {}

