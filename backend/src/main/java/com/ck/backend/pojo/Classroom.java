package com.ck.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 教室表(Classroom)实体类
 *
 * @author makejava
 * @since 2024-02-27 14:02:58
 */
@Data
@Component
public class Classroom implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;
    /**
     * 教室名称
     */
    private String name;
    /**
     * 教室人数
     */
    private Integer num;
    /**
     * 教师
     */
    private String teacher;
    /**
     * 使用班级
     */
    private String classs;


}

