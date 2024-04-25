package com.ck.backend.mapper;

import com.ck.backend.entity.Question;
import com.ck.backend.entity.QuestionCourse;
import com.ck.backend.entity.Questionbank;
import com.ck.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionBankMapper {
    List<Question> selectQuestionList();

    List<QuestionCourse> selectQuestion();

    List<Questionbank> getReportByNumber(String number);
    List<Questionbank> selectQuestionById(@Param("groupId") int groupId,@Param("userId") int userId);

    List<Questionbank> selectQuestionByIdTwo(@Param("groupId") int groupId);

    int insertEmp(Questionbank questionbank);
    int getCount(String number);
    List<User> setByName(String username);

    List<Questionbank> FillAnswersByNumber(@Param("userId")int userId);

    List<Questionbank> getQuestionBank(@Param("username")String username,@Param("userId")int userId);

    List<Questionbank> getQuestionBankByNumber(@Param("number")String number,@Param("username")String username,@Param("userId")int userId);

    List<Questionbank> getFillAnswers(@Param("number")String number,@Param("username")String username,@Param("userId")int userId);

    List<Questionbank> getFillAnswersAll(@Param("number")String number,@Param("username")String username,@Param("userId")int userId);

    List<Questionbank> getFillAll(@Param("groupId")String groupId,@Param("username")String username,@Param("userId")int userId);

    List<Questionbank> getFill(@Param("number")String number,@Param("username")String username,@Param("userId")int userId);

    List<Questionbank> QuestionBank(int groupId);

    List<Questionbank> QuestionCourse(@Param("groupId")String groupId,@Param("username")String username,@Param("userId")int userId);

    List<Questionbank> getFillAllTwo(@Param("number")int number,@Param("username")String username,@Param("userId")int userId);
}
