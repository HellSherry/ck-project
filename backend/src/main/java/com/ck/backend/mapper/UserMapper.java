package com.ck.backend.mapper;


import com.ck.backend.pojo.User;
import jakarta.websocket.Session;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mapper//在运行时会自动生成该接口的实现类对象（代理对象），并将改对象交给ioc容器管理
public interface UserMapper {
    /*   @Results({
                @Result(column = "dept_id",property = "deptId")
        })通过@Results，注解手动映射封装
    */
    // @Select("SELECT * from cellPhone where color like concat('%',#{color},'%')")

    @Select("select * from user where  username = #{username} and password = #{password}")
    public User login(String username, String password);

    @Select("select * from user")
    public List<User> selectAll();

    @Options(keyProperty = "userId", useGeneratedKeys = true)
    @Insert("INSERT INTO `user` ( `userEmail`, `username`, `password`, `grade`, `userImg`, `defaultAddress`, `identity`) VALUES (#{userEmail}, #{username}, #{password}, #{grade}, #{userImg}, #{defaultAddress}, #{identity});")
    public Integer regist(User user);

    @Select("select * from `user` where  username = #{username} or userEmail =#{username} or userId =#{username}")
    public User selectOne(String username);

    @Select("select * from `user` where  userId =#{userId}")
    public User selectById(Integer userId);

    Integer update(User user);

    List<User> selectByIds(Integer page, @Param("ids") String ids);

    List<User> selectLimit(Integer page, Integer identity, String search);

    Integer count(Integer identity, String search);

    Integer deletes(@Param("ids") String ids);

    Integer insert(User user);

    @Select("select password from  user where userId =#{userId}")
    String getPasswordById(Integer userId);
//    @Delete("delete  from  cellphone where  id = #{id}")
//    public int delete(Integer id);
//    @Options(keyProperty = "id" ,useGeneratedKeys = true)//获取返回的主键
//    @Insert("INSERT INTO cellphone ( `name`, `brand`, `price`, `publish`, `color`) VALUES (#{name}, #{brand}, #{price}, #{publish}, #{color})")
//    public int insert(Phone phone);

}
