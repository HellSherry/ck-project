package com.ck.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Soft {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String particulars;
    private String images;
    private String downurl;
    private String icon;
    private String company;
    private Double size;
    private Integer quantity;
    private Date addtime;
}
