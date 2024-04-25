package com.ck.backend.controller;

 import com.ck.backend.controller.websokect.WebSokectServer;
import com.ck.backend.mapper.AIMapper;
import com.ck.backend.util.QWENUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.ck.backend.util.ResponseEntity;
  import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HellSherrry
 * &#064;date 2023/12/25-19:55
 * @version 1.0
 * &#064;description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/ai")
public class AIController {

    @Autowired
    private AIMapper aiMapper;
    @Resource
    private WebSokectServer webSocket;

    @RequestMapping(value = "webAi")
    public void asdfsad(String ask) throws UnsupportedEncodingException {
        webSocket.sendAllMessage(ask);
    }

    @RequestMapping(value = "qwen")
    public ResponseEntity qwen(String ask) throws UnsupportedEncodingException {
        return ResponseEntity.ok(QWENUtils.getAnswer(ask)) ;
    }

    @RequestMapping(value = "chatgpt")
    public Object apiProxyGet(String ask, HttpServletRequest request) throws UnsupportedEncodingException {

        String res = aiMapper.getOneAi(ask);
        if (res == null) {
            String target = "https://v1.apigpt.cn?apitype=sql&q=" + ask;

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

            org.springframework.http.ResponseEntity<String> response = restTemplate.getForEntity(target, String.class, entity);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> list = null;
            try {
                list = mapper.readValue(response.getBody(), Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            res = (String) list.get("ChatGPT_Answer");

            aiMapper.InsertOne(ask, res);

        }


//        String target = "http://ovoa.cc/api/Bing.php";

//
//        // 当需要分页时，返回分页信息
//        res.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//        res.setHeader("Content-Range", String.valueOf(list.size()));
//        res.setHeader("Access-Control-Expose-Headers", "Content-Range");

        return new com.ck.backend.util.ResponseEntity<String>(res);
    }

}
