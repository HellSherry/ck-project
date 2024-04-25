package com.ck.backend.mapper;

import com.ck.backend.pojo.Answer;
import com.ck.backend.pojo.Paper;
import com.ck.backend.pojo.PaperClass;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (Paper)表数据库访问层
 *
 * @author hellSherry
 * @since 2023-12-28 14:27:35
 */
@Mapper
public interface PaperMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param paperid 主键
     * @return 实例对象
     */
    Paper queryById(Integer paperid);


    @Insert("Insert  into paperclass(paperid,classid) values(#{paperid},#{classid})")
    int insertPaperClass(Integer paperid, Integer classid);

    /**
     * 查询指定行数据
     *
     * @param paper    查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Paper> queryAllByLimit(@Param("paper") Paper paper, @Param("pageable") Pageable pageable);

    @Select("SELECT p.paperid,papername,p.starttime,p.totalscore,p.duration from paper p left join paperclass pc on p.paperid=pc.paperid LEFT JOIN `user` u on  u.classid=pc.classid where NOW()<=DATE_ADD(DATE_ADD(p.starttime, INTERVAL p.duration MINUTE), INTERVAL 8 HOUR) and u.userId=#{id} order by p.starttime ")
    List<Paper> selectListById(Integer id);

//    @Select("SELECT p.paperid,papername,p.starttime,p.totalscore,p.duration from paper p left join paperclass pc on p.paperid=pc.paperid LEFT JOIN `user` u on  u.classid=pc.classid where NOW()>DATE_ADD(DATE_ADD(p.starttime, INTERVAL p.duration MINUTE), INTERVAL 8 HOUR) and u.userId=#{id} order by p.starttime desc limit ${(page-1)*10},10 ")
    List<Paper> selectHistoryListByIdLimit(Integer id,@Param("page") Integer page);

    List<Paper> selectAllByLimit(String search, Integer page, String starttime, String endtime);

    @Select("SELECT COUNT(1)  FROM `user`  u  LEFT JOIN  `paperclass` p on u.classid =p.classid WHERE u.userId= #{userId} and p.paperid=#{paperid}")
    int findOutIfItExists(int paperid, int userId);

    @Select("SELECT score FROM `answer`  WHERE userId=#{userId} and paperId=#{paperid}")
Double getScore(int paperid,Integer userId) ;

   public   int insertAnswer( Answer answer);


    List<PaperClass> selectPaperClassById(Integer paperid);

    /**
     * 统计总行数
     *
     * @param search 查询条件
     * @return 总行数
     */
    int count(String search, String starttime, String endtime);


    /**
     * 新增数据
     *
     * @param paper 实例对象
     * @return 影响行数
     */
    @Options(keyProperty = "paperid", useGeneratedKeys = true)
    int insert(Paper paper);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Paper> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Paper> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Paper> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Paper> entities);

    /**
     * 修改数据
     *
     * @param paper 实例对象
     * @return 影响行数
     */
    int update(Paper paper);

    /**
     * 通过主键删除数据
     *
     * @param paperid 主键
     * @return 影响行数
     */
    int deleteById(Integer paperid);

    Paper getExamById(Integer paperId);

    @Select("select totalscore from paper where  paperid=#{paperId}")
    Integer getExamFractionById(Integer paperId);


    Answer selectGradeByPaperId(Integer userId,Integer paperId);

    List<Answer> selectGrades(Integer paperId,Integer classId);
}

