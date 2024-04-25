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
public class Courseschedule {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer classid;
  private String detail;
  private String name;

}
