package com.ck.backend.listerer;

import com.ck.backend.controller.websokect.SpringWebSokectController;
import jakarta.annotation.Resource;
import jakarta.websocket.Session;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HellSherrry
 * &#064;date 2023/12/18-16:44
 * @version 1.0
 * &#064;description
 */
@Component
    public class KeyListenter extends KeyExpirationEventMessageListener {

    @Resource
    SpringWebSokectController  springWebSokectController;
    public KeyListenter(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        keyExpire(expiredKey);
        System.out.println("expiredKey = " + expiredKey);
    }
    public void keyExpire(String key) {

        if (key.contains(":") && Objects.equals(key.split(":")[0], "login")){
            String s = key.split(":")[1];
            springWebSokectController.sendOneMessage(s,"loginOut");
            SpringWebSokectController.closeOneConnect(s,false);
        }else
            System.out.println("key:" + key + " 过期了");
        }
}
