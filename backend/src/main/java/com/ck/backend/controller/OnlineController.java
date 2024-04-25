package com.ck.backend.controller;

import com.ck.backend.controller.websokect.SpringWebSokectController;
import com.ck.backend.mapper.UserMapper;
import com.ck.backend.util.PageBean;
import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author HellSherrry
 * &#064;date 2024/1/12-10:54
 * @version 1.0
 * &#064;description
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/online")
public class OnlineController {
    @Resource
    private SpringWebSokectController webSocket;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/delete")
    public ResponseEntity delete(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            redisTemplate.expire("login:" + id, 1, TimeUnit.SECONDS );
        }
        return ResponseEntity.ok(1);
    }
    @RequestMapping("/getUser")
    public ResponseEntity send(Integer page){


        ConcurrentHashMap<String, Session> sessionPool = webSocket.getSessionPool();
        if (sessionPool.size() == 0){
            return ResponseEntity.ok("no user");
        }
        return  new ResponseEntity<>( new PageBean<>(null,null,sessionPool.size(), userMapper.selectByIds( page,String.join(", ", sessionPool.keySet())) )) ;
    }
}
