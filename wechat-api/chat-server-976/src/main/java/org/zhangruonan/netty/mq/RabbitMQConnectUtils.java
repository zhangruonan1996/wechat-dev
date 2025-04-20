package org.zhangruonan.netty.mq;

import com.rabbitmq.client.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 12:34:10
 */
public class RabbitMQConnectUtils {

    private final List<Connection> connections = new ArrayList<>();
    private final int maxConnections = 20;

    // 开发环境 dev
    private final String host = "127.0.0.1";
    private final int port = 5672;
    private final String username = "zrn";
    private final String password = "zrn1996";
    private final String virtualHost = "wechat-dev";

    public ConnectionFactory factory;

    public ConnectionFactory getRabbitMQConnection() {
        return getFactory();
    }

    public ConnectionFactory getFactory() {
        initFactory();
        return factory;
    }

    private void initFactory() {
        try {
            if (factory == null) {
                factory = new ConnectionFactory();
                factory.setHost(host);
                factory.setPort(port);
                factory.setUsername(username);
                factory.setPassword(password);
                factory.setVirtualHost(virtualHost);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsg(String message, String queue) throws Exception{
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish("",
                queue,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("utf-8"));
        channel.close();
        setConnection(connection);
    }

    public void sendMsg(String message, String exchange, String routingKey) throws Exception{
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish(exchange,
                routingKey,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("utf-8"));
        channel.close();
        setConnection(connection);
    }

    public GetResponse basicGet(String queue, boolean autoAck) throws Exception{
        GetResponse getResponse = null;
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        getResponse = channel.basicGet(queue, autoAck);
        channel.close();
        setConnection(connection);
        return getResponse;
    }

    public Connection getConnection() throws Exception{
        return getAndSetConnection(true, null);
    }

    public void setConnection(Connection connection) throws Exception{
        getAndSetConnection(false, connection);
    }

    private synchronized Connection getAndSetConnection(boolean isGet, Connection connection) throws Exception{
        getRabbitMQConnection();

        if (isGet) {
            if (connections.isEmpty()) {
                return factory.newConnection();
            }
            Connection newConnection = connections.get(0);
            connections.remove(0);
            if (newConnection.isOpen()) {
                return newConnection;
            } else {
                return factory.newConnection();
            }
        } else {
            if (connections.size() < maxConnections) {
                connections.add(connection);
            }
            return null;
        }
    }

}
