package com.ck.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Specia)实体类
 *
 * @author makejava
 * @since 2023-12-28 09:36:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specia implements Serializable {
/**
     * 专业ID
     */
    private Integer id;
/**
     * 专业名
     */
    private String name;


}

