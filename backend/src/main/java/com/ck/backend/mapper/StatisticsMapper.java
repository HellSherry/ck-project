package com.ck.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * @author HellSherrry
 * &#064;date 2023/12/25-20:09
 * @version 1.0
 * &#064;description
 */
@Mapper
public interface StatisticsMapper {

    @Select("select COUNT(1) as value,specia. name from class_info  LEFT JOIN specia on specia.id=class_info.speciaid GROUP BY speciaid")
    public List<HashMap> getClassCountAsSpecil();

    @Select("select COUNT(1) as value,specia. name from question_course  LEFT JOIN specia on specia.id=question_course.speciaid GROUP BY speciaid")
    public List<HashMap> getCourseCountAsSpecil();

    @Select("select COUNT(1) from class_info")
    Integer getClassCount();

    @Select("select COUNT(1) from specia")
    Integer getSpecialCount();

    @Select("select COUNT(1) from answer")
    Integer getAnswerCount();

    @Select("select COUNT(1) from message ")
    Integer getMessageCount();

    @Select("select COUNT(1) from paper")
    Integer getPaperCount();

    @Select("select COUNT(1) from question")
    Integer getQuestionCount();

    @Select("select COUNT(1) from question_course")
    Integer getCoutseCount();

    @Select("select COUNT(1) from question_group")
    Integer getGroupCount();

    @Select("select COUNT(1) from user where identity=1")
    Integer getStudentCount();

    @Select("select COUNT(1) from user where identity=2")
    Integer getTeacherCount();

    @Select("select COUNT(1) from user where identity=3")
    Integer getAdminCount();

    @Select("SELECT COUNT(1) AS  value,identity as name FROM `user` GROUP BY  identity")
    public List<HashMap> getUserCount();
}
