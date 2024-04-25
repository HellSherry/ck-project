package com.ck.backend.mapper;

import com.ck.backend.pojo.QuestionAsk;
import com.ck.backend.pojo.QuestionResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.relational.core.sql.In;

import java.util.HashMap;
import java.util.List;

/**
 * @author HellSherrry
 * &#064;date 2024/1/6-18:12
 * @version 1.0
 * &#064;description
 */
@Mapper
public interface AskMapper {
    public int selectCount(String search);

    public List<QuestionAsk> selectLimit(String search, Integer page,String isHot);
    public QuestionAsk selectById(Integer page);

    List<QuestionResponse> selectDetail(Integer id);


    int addOne(QuestionAsk questionAsk);

    int addOneRes(Integer askId, String context, Integer userId);

    List<HashMap> selectReply(Integer userId);
}
