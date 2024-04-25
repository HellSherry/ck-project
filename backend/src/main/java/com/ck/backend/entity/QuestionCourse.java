package com.ck.backend.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@Mapper
public class QuestionCourse {

  private long id;
  private String name;
  private String courseDescription;
  private long speciaId;


}
