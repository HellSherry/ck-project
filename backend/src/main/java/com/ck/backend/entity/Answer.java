package com.ck.backend.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Data
@Mapper
public class Answer {

  private long id;
  private long userId;
  private long paperId;
  private String answerContent;
  private double score;
  private Timestamp answerTime;



}
