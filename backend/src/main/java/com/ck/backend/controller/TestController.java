package com.ck.backend.controller;

import com.ck.backend.controller.websokect.SpringWebSokectController;
import com.ck.backend.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HellSherrry
 * &#064;date 2023/12/18-12:23
 * @version 1.0
 * &#064;description
 */
@RestController
@CrossOrigin(origins = "*")
public class TestController {
    @Resource
private SpringWebSokectController webSocket;

    @RequestMapping("/public/haha")
    public void haha(String msg){
        webSocket.sendAllMessage(msg);
        }

    @RequestMapping(value="/public/bing")
    public Object  apiProxyGet (HttpServletRequest request, HttpServletResponse res) throws UnsupportedEncodingException {
        // 获取请求url
        String url = request.getRequestURL().toString();
        // 获取url中携带的参数，即/resTemplate?后面的内容
        String query = request.getQueryString();


//        String target = "http://ovoa.cc/api/Bing.php";
        String target = "https://v1.apigpt.cn/";

        // 新的url拼接上旧url后的请求参数
        if (query != null && !query.equals("") && !query.equals("null")) {
            target = target + "?" + query;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        // 获取到请求头
        Enumeration<String> headerNames = request.getHeaderNames();

        HttpHeaders headers = new HttpHeaders();
        Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String header = request.getHeader(headerName);
            headerMap.put(headerName, header);
            headers.add(headerName, header);
        }

        // 构造HttpEntity，新请求会携带本次请求的请求头
        HttpEntity entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.getForEntity(target, String.class, entity);
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> list  = null;
//        try {
//            list = mapper.readValue(response.getBody(), Map.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        // 当需要分页时，返回分页信息
//        res.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//        res.setHeader("Content-Range", String.valueOf(list.size()));
//        res.setHeader("Access-Control-Expose-Headers", "Content-Range");

        return response.getBody();
    }


    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/hello")
    public String hello(){
//        webSocket.sendAllMessage("hello");
        String key="1";
            ConcurrentHashMap<String, Session> sessionPool = SpringWebSokectController.getSessionPool();

            if (sessionPool.containsKey(key)) {
                try {
                    sessionPool.get(key).close();
                } catch (IOException e) {
                  return   "error";
                }
            }

        return "hello";
    }
    @RequestMapping("/token")
    public String token(){
        return "token";
    }
    @RequestMapping("/test")
    @PreAuthorize("hasAuthority('test')")
    public String test(){
        return "test";
    }
    @RequestMapping("/no")
    @PreAuthorize("hasAuthority('nooooo')")
    public String no(){
        return "no";
    }
}
