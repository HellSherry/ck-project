package com.ck.backend.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Answer {
  private Integer userId;
  private Integer paperId;
  private String answerContent;
  private Double score;
  private Integer answerTime;
}
