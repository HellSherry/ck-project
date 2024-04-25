package com.ck.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * (Question)实体类
 *
 * @author hellSherry
 * @since 2023-12-28 14:44:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question implements Serializable {
    private Integer id;
    /**
     * 问题
     */
    private String question;
    /**
     * 答案
     */
    private String answers;
    /**
     * 正确答案
     */
    private String ranswer;
    /**
     * 分组id
     */
    private Integer groupid;
    /**
     * 1-5
     */
    private Integer difficulty;
    /**
     * 0代表废除 1启用
     */
    private Integer enable;
    /**
     * 题目解析
     */
    private String parse;
}

