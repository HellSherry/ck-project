package com.ck.backend.pojo;

import java.sql.Timestamp;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * (Message)实体类
 *
 * @author hellSherry
 * @since 2024-01-10 13:14:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String details;

     private Date addtime;

}

