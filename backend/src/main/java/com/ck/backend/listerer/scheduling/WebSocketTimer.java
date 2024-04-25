package com.ck.backend.listerer.scheduling;

import com.ck.backend.controller.websokect.SpringWebSokectController;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author HellSherrry
 * &#064;date 2023/12/21-8:55
 * @version 1.0
 * &#064;description
 */

//@Component
public class WebSocketTimer {
    @Resource
    private SpringWebSokectController webSocket;

    @Scheduled(cron ="*/6 * * * * ?")
    public void sayHello() {
        webSocket.sendAllMessage("");
            System.out.println("hello");
    }

}
