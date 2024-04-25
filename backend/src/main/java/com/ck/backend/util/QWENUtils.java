package com.ck.backend.util;

import com.alibaba.dashscope.aigc.conversation.Conversation;
import com.alibaba.dashscope.aigc.conversation.ConversationParam;
import com.alibaba.dashscope.aigc.conversation.ConversationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.utils.JsonUtils;

/**
 * @author HellSherrry
 * &#064;date 2024/1/10-8:48
 * @version 1.0
 * &#064;description
 */

public class QWENUtils {
    public static void main(String[] args) {
        System.out.println(getAnswer("用java写一个helloword"));
    }
    public static String getAnswer(String prompt){
        Constants.apiKey="sk-2fe38b08f3794a89ba459b3a39303cd3";
        Conversation conversation = new Conversation();
        if (prompt == null) {
            return null;
        }
//        String prompt = "用java写一个helloword";
        ConversationParam param = ConversationParam
                .builder()
                .model(Conversation.Models.QWEN_TURBO)
                .prompt(prompt)
                .build();
        ConversationResult result = null;
        try {
            result = conversation.call(param);
        } catch (InputRequiredException | NoApiKeyException e) {
            throw new RuntimeException(e);
        }
        return JsonUtils.toJson(result);
    }
}
