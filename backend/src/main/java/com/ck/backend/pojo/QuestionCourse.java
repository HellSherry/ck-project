package com.ck.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * (QuestionCourse)实体类
 *
 * @author hellSherry
 * @since 2023-12-29 19:15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionCourse implements Serializable {

    private Integer id;

    private String name;

    private String coursedescription;

    private Integer speciaid;

    private List<QuestionGroup> questionGroups;

}

