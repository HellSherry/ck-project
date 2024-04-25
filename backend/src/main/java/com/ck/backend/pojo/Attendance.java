package com.ck.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生考勤表(Attendance)实体类
 *
 * @author makejava
 * @since 2024-02-27 14:01:29
 */
@Data
@Component
public class Attendance implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 学生
     */
    private String stedent;
    /**
     * 班级
     */
    private String classs;
    /**
     * 考勤日期
     */
    private String date;
    /**
     * 考勤时间
     */
    private String time;
    /**
     * 状态
     */
    private String status;
    /**
     * 详情
     */
    private String details;


}

