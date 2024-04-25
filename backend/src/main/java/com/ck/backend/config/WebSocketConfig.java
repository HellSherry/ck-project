package com.ck.backend.config;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.Collections;
import java.util.List;

/**
 * @author HellSherrry
 * &#064;date 2023/12/18-8:51
 * @version 1.0
 * &#064;description
 */
@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator{
    @Bean
    public ServerEndpointExporter serverEndpointConfig() {
        return new ServerEndpointExporter();
    }
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        //获取请求头
        List<String> s = request.getHeaders().get("Sec-WebSocket-Protocol");

        //当Sec-WebSocket-Protocol请求头不为空时,需要返回给前端相同的响应

        response.getHeaders().put("Sec-WebSocket-Protocol",s);

        /**
         *获取请求头后的逻辑处理
         */

        super.modifyHandshake(sec, request, response);
    }
}