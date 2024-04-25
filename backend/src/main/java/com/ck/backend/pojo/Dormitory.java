package com.ck.backend.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 宿舍表(Dormitory)实体类
 *
 * @author makejava
 * @since 2024-02-27 14:02:59
 */
@Data
@Component
public class Dormitory implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 宿舍号
     */
    private String name;
    /**
     * 人数
     */
    private Integer num;
    /**
     * 宿舍性别
     */
    private String gender;
    /**
     * 可容纳人数
     */
    private Integer max;


}

