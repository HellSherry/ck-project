package com.ck.backend.service.impl;

import com.ck.backend.domain.LoginUser;
import com.ck.backend.mapper.UserMapper;
import com.ck.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author HellSherrry
 * &#064;date 2023/12/17-11:25
 * @version 1.0
 * &#064;description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired//运行时，Ioc容器自动注入
//    @Resource(name="phoneDaoImp")//jdk提供的
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(username);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名错误");
        }
        //todo 查询权限信息
        List<String> list =new ArrayList<>(Arrays.asList("admin","test"));

        return new LoginUser(user,list);

    }
}
