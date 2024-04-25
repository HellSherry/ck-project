package com.ck.backend.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Data
@Mapper
public class Paper {

  private long paperid;
  private String papername;
  private String questions;
  private String creatorusername;
  private long totalscore;
  private Timestamp starttime;
  private long duration;


}
