package com.ck.backend.controller;

import com.ck.backend.controller.websokect.SpringWebSokectController;
import com.ck.backend.domain.LoginUser;
import com.ck.backend.mapper.AIMapper;
import com.ck.backend.mapper.AskMapper;
import com.ck.backend.pojo.Paper;
import com.ck.backend.pojo.QuestionAsk;
import com.ck.backend.util.PageBean;
import com.ck.backend.util.QWENUtils;
import com.ck.backend.util.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HellSherrry
 * &#064;date 2024/1/6-18:10
 * @version 1.0
 * &#064;description
 */
@RestController
@CrossOrigin("*")
@RequestMapping("askQuestion")
public class QuestionAskController {

    @Autowired
    private AIMapper aiMapper;
    @Resource
    private AskMapper askMapper;
    @Resource
    SpringWebSokectController springWebSokectController;
    @RequestMapping("add")
    public ResponseEntity add(@NotNull @AuthenticationPrincipal LoginUser customUser, String context, HttpServletRequest request) throws JsonProcessingException {

//        String res = aiMapper.getOneAi(context);
        QuestionAsk questionAsk = new QuestionAsk(null, 0, context, customUser.getUser().getUserId(), null);
        askMapper.addOne(questionAsk);
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap mapp=(HashMap)objectMapper.readValue(QWENUtils.getAnswer(context), HashMap.class).get("output");





//        if (res == null) {
//            String target = "http://ovoa.cc/api/Bing.php?msg="+context;
//
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//
//            // 获取到请求头
//            Enumeration<String> headerNames = request.getHeaderNames();
//
//            HttpHeaders headers = new HttpHeaders();
//            Map<String, String> headerMap = new HashMap<>();
//            while (headerNames.hasMoreElements()) {
//                String headerName = headerNames.nextElement();
//                String header = request.getHeader(headerName);
//                headerMap.put(headerName, header);
//                headers.add(headerName, header);
//            }
//
//            // 构造HttpEntity，新请求会携带本次请求的请求头
//            HttpEntity entity = new HttpEntity<String>(headers);
//
//            org.springframework.http.ResponseEntity<String> response = restTemplate.getForEntity(target, String.class, entity);
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String, Object> list  = null;
//            try {
//                list = mapper.readValue(response.getBody(), Map.class);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("list = " + list);
//            res= (String) list.get("content");
//            aiMapper.InsertOne(context,res);
//}

        askMapper.addOneRes(questionAsk.getId(),  mapp.get("text").toString(), -1);
        springWebSokectController.sendOneMessage(customUser.getUser().getUserId().toString(),"newReply:"+questionAsk.getId());
        return ResponseEntity.ok(null);
    }

    @RequestMapping("addRes")
    public ResponseEntity addRes(@AuthenticationPrincipal LoginUser customUser, String context, Integer askId,String askUser) {
//        newReply

        springWebSokectController.sendOneMessage(askUser,"newReply:"+askId);
        return ResponseEntity.ok(askMapper.addOneRes(askId, context, customUser.getUser().getUserId()));
    }

    @RequestMapping("get")
    public ResponseEntity queryByPage(String search, Integer page, String isHot) {
        return ResponseEntity.ok(new PageBean(null, null, askMapper.selectCount(search), askMapper.selectLimit(search, page, isHot)));
    }

    @RequestMapping("getById")
    public ResponseEntity getById(Integer id) {
        return ResponseEntity.ok(askMapper.selectById(id));
    }

    @RequestMapping("getDetail")
    public ResponseEntity getDetail(Integer id) {
        return ResponseEntity.ok(askMapper.selectDetail(id));
    }
    @RequestMapping("getReply")
    public ResponseEntity getReply(@AuthenticationPrincipal LoginUser customUser) {
        return ResponseEntity.ok(askMapper.selectReply(customUser.getUser().getUserId()));
    }
}
