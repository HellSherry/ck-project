package com.ck.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * (Specia)实体类
 *
 * @author hellSherry
 * @since 2023-12-29 11:26:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Specia implements Serializable {
    /**
     * 专业ID
     */
    private Integer id;
    /**
     * 专业名
     */
    private String name;
    private List<QuestionCourse>  questionCourse;

}

