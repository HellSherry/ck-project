package com.ck.backend.listerer;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author HellSherrry
 * &#064;date 2023/12/19-14:48
 * @version 1.0
 * &#064;description
 */
@Component
public class MessageListererImpl implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("收到信息了");
//        得到监听到消息/
        String msg= new String(message.getBody());
        System.out.println("msg = " + msg);
//    得到监听频道
        String channel= new String(message.getChannel());
        System.out.println("channel = " + channel);
    }
}
