package com.ck.backend.filter;


import com.ck.backend.domain.LoginUser;
import com.ck.backend.pojo.User;
import com.ck.backend.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author HellSherrry
 * &#064;date 2023/12/17-20:47
 * @version 1.0
 * &#064;description
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            token = request.getHeader("Sec-Websocket-Protocol");
        }
        System.out.println("token = " + token);
        List<String> identity = new ArrayList<>(Arrays.asList("admin", "test"));
        if (StringUtils.hasText(token)) {
            //解析token
            User user;
            try {
                ObjectMapper mapper = new ObjectMapper();
                user = mapper.readValue(JwtUtil.extractUsername(token), User.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("token 出错");
            }
            switch (user.getIdentity()) {
                case 0:
                    identity.add("student");
                    break;
                case 1:
                    identity.add("teacher");
                    break;
                case 2:
                    identity.add("teleadmin");
                    break;
            }
            if (Objects.equals(redisTemplate.opsForValue().get("login:" + user.getUserId()), token)) {
                redisTemplate.expire("login:" + user.getUserId(), 7, TimeUnit.DAYS );
                System.out.println("user = " + user);
                LoginUser loginUser = new LoginUser(user, identity);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }  else {
            String kToken = request.getHeader("token");
            if (StringUtils.hasText(kToken)) {
                identity.add("examimg");
                String[] split = new String(Base64.getDecoder().decode(kToken.getBytes())).split(".");
                ObjectMapper mapper = new ObjectMapper();
                User user = mapper.readValue(JwtUtil.extractUsername((String) redisTemplate.opsForValue().get("login:" + split[0])), User.class);
                LoginUser loginUser = new LoginUser(user, identity);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //放行
        filterChain.doFilter(request, response);
    }
}
