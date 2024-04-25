package com.ck.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * 作息管理表(Schedule)实体类
 *
 * @author makejava
 * @since 2024-02-27 14:02:59
 */
@Data
@Component
public class Schedule implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;
    /**
     * 课程ID
     */
    private String name;
    /**
     * 开始时间
     */
    private Time starttime;
    /**
     * 结束时间
     */
    private Time endtime;
    /**
     * 描述
     */
    private String Description;


}

