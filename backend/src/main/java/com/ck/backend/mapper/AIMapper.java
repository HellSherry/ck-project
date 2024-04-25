package com.ck.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author HellSherrry
 * &#064;date 2023/12/25-20:09
 * @version 1.0
 * &#064;description
 */
@Mapper
public interface AIMapper {
    @Select("select res from ai where ask=#{ask}")
   public String getOneAi(String ask);
    @Insert("insert into ai(ask,res) values(#{ask},#{res})")
    public int InsertOne(String ask,String res);
}
