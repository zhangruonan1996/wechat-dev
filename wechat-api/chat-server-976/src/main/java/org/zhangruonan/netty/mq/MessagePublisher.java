package org.zhangruonan.netty.mq;

import org.zhangruonan.netty.ChatMsg;
import org.zhangruonan.utils.JsonUtils;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 12:34:17
 */
public class MessagePublisher {

    /**
     * 定义交换机的名字
     */
    public static final String TEST_EXCHANGE = "test_exchange";

    /**
     * 定义队列的名字
     */
    public static final String TEST_QUEUE = "test_queue";

    /**
     * 发送信息到消息队列接收并且保存到数据库的路由地址
     */
    public static final String ROUTING_KEY_WECHAT_MESSAGE_SEND = "zhangruonan.wechat.wechat.msg.send";

    public static void sendMsgToSave(ChatMsg msg) throws Exception {
        RabbitMQConnectUtils connectUtils = new RabbitMQConnectUtils();
        connectUtils.sendMsg(JsonUtils.objectToJson(msg), TEST_EXCHANGE, ROUTING_KEY_WECHAT_MESSAGE_SEND);
    }

}

