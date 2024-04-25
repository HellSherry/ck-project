package com.ck.backend.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@Mapper

public class Questionbank {

  private String id;
  private String cName;
  private String gname;
  private String question;
  private String answers;
  private String ranswer;
  private long difficulty;
  private long enable;
  private String parse;
  private String response;
  private String number;
  private String submissionTime;
  private String questionId;
  private String count;
  private String answer1;
  private String answer2;
  private String answer3;
  private String answer4;
  private String correct;
  private int courseid;
  private String user;
  private int userId;
  private int groupId;
  private int grades;

}
