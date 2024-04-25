package com.ck.backend.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * (Paper)实体类
 *
 * @author hellSherry
 * @since 2023-12-28 14:27:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Paper implements Serializable {

    /**
     * 试卷id
     */
    private Integer paperid;
    /**
     * 试卷名称
     */
    private String papername;
    /**
     * 题目
     */
    private String questions;
    /**
     * 创建者名称
     */
    private String creatorusername;
    /**
     * 总分
     */
    private Integer totalscore;
    /**
     * 开始时间
     */
    private Date starttime;
    /**
     * 考试时长
     * 考试时长
     */
    private Integer duration;
    /**
     * 参考班级
     */
    private String classid;

    private List<PaperClass> paperClasses;


}

