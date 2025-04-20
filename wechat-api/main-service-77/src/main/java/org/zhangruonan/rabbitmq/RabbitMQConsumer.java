package org.zhangruonan.rabbitmq;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.zhangruonan.netty.ChatMsg;
import org.zhangruonan.service.ChatMessageService;
import org.zhangruonan.utils.JsonUtils;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 11:53:17
 */
@Component
@Slf4j
public class RabbitMQConsumer {

    @Resource
    private ChatMessageService chatMessageService;

    @RabbitListener(queues = {RabbitMQTestConfig.TEST_QUEUE})
    public void watchQueue(String payload, Message message) {

        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        log.info("routingKey: {}", routingKey);

        if (routingKey.equals(RabbitMQTestConfig.ROUTING_KEY_WECHAT_MESSAGE_SEND)) {
            String msg = payload;
            ChatMsg chatMsg = JsonUtils.jsonToPojo(msg, ChatMsg.class);
            log.info(chatMsg.toString());

            chatMessageService.saveMsg(chatMsg);
        }
    }

}
