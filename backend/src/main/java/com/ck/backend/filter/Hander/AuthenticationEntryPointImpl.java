package com.ck.backend.filter.Hander;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author HellSherrry
 * &#064;date 2023/12/18-18:28
 * @version 1.0
 * &#064;description
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                response.setStatus(200);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().append("{\"code\":401,\"msg\":\"登录状态失效，请重新登录\" }");
    }
}
