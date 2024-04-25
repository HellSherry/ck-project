package com.ck.backend.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@Mapper
public class QuestionGroup {

  private long id;
  private String name;
  private long courseid;

}
