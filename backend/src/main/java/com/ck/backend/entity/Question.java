package com.ck.backend.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@Mapper
public class Question {

  private long id;
  private String question;
  private String answers;
  private String ranswer;
  private long groupId;
  private long difficulty;
  private long enable;
  private String parse;
  private String name;
  private int courseid;

}
