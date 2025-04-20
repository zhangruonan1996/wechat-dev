package org.zhangruonan.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 13:13:46
 */
@Configuration
public class RabbitMQTestConfig {

    /**
     * 定义交换机的名字
     */
    public static final String TEST_EXCHANGE = "test_exchange";

    /**
     * 定义队列的名字
     */
    public static final String TEST_QUEUE = "test_queue";

    /**
     * 具体的路由地址
     */
    public static final String ROUTING_KEY_TEST_SEND = "zhangruonan.wechat.test.send";

    /**
     * 发送信息到消息队列接收并且保存到数据库的路由地址
     */
    public static final String ROUTING_KEY_WECHAT_MESSAGE_SEND = "zhangruonan.wechat.wechat.msg.send";

    /**
     * 创建交换机
     *
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 13:15:06
     */
    @Bean(TEST_EXCHANGE)
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(TEST_EXCHANGE).durable(true).build();
    }

    /**
     * 创建队列
     *
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 13:15:46
     */
    @Bean(TEST_QUEUE)
    public Queue queue() {
        return QueueBuilder.durable(TEST_QUEUE).build();
    }

    /**
     * 定义队列绑定到交换机的关系
     *
     * @param exchange
     * @param queue
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 13:16:54
     */
    @Bean
    public Binding binding(@Qualifier(TEST_EXCHANGE) Exchange exchange, @Qualifier(TEST_QUEUE) Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("zhangruonan.wechat.#").noargs();
    }

}
