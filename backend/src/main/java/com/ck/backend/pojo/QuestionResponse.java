package com.ck.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

  private Integer id;
  private Integer askId;
  private String answer;
  private Integer creatorUser;
  private java.sql.Timestamp creatTime;


}
