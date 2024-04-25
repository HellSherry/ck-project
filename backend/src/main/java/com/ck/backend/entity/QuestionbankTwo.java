package com.ck.backend.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@Mapper

public class QuestionbankTwo{

    private String id;
    private String cName;
    private String gName;
    private String question;
    private String answers;
    private String ranswer;
    private long difficulty;
    private long enable;
    private String parse;
    private String questionId;
    private String response;
    private String count;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String longestScore;
    private String user;
    private int userId;
}
