package com.ck.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * (ClassInfo)实体类
 *
 * @author hellSherry
 * @since 2023-12-29 11:17:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassInfo implements Serializable {
    private Integer id;

    private String name;

    private Integer speciaid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpeciaid() {
        return speciaid;
    }

    public void setSpeciaid(Integer speciaid) {
        this.speciaid = speciaid;
    }

}

