package com.ck.backend.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageBean<T> {

    // 1.当前页数 从页面获取
    private Integer currentPage;
    // 2.每页显示数据个数，赋初值或者setter获取
    private Integer currentCount;
    // 3.总条数，从数据库获取
    private Integer total;
    // 5.每页的显示数据，数据库得到
    List<T> list = new ArrayList<>();
}