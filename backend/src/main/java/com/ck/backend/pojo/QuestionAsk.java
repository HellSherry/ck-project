package com.ck.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionAsk implements Serializable {
    private Integer id;
    private Integer num;
    private String question;
    private Integer creatorUser;
    private java.sql.Timestamp creatTime;
}
