package org.zhangruonan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.zhangruonan.netty.utils.JedisPoolUtils;
import org.zhangruonan.netty.websocket.WebsocketServerInitializer;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Netty服务启动类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-17 20:51:49
 */
public class ChatServer2 {

    public static final Integer nettyDefaultPort = 976;

    public static final String initOnlineCounts = "0";

    public static Integer selectPort(Integer port) {
        String portKey = "netty_port";
        Jedis jedis = JedisPoolUtils.getJedis();

        Map<String, String> portMap = jedis.hgetAll(portKey);
        // System.out.println(portMap);

        List<Integer> portList = portMap.entrySet()
                .stream()
                .map(entry -> Integer.valueOf(entry.getKey()))
                .collect(Collectors.toList());


        Integer nettyPort = null;

        if (portList == null || portList.isEmpty()) {
            jedis.hset(portKey, String.valueOf(port), initOnlineCounts);
            nettyPort = port;
        } else {
            Optional<Integer> maxInteger = portList.stream().max(Integer::compareTo);
            int maxPort = maxInteger.get().intValue();
            Integer currentPort = maxPort + 10;
            jedis.hset(portKey, String.valueOf(currentPort), initOnlineCounts);
            nettyPort = currentPort;
        }

        return nettyPort;
    }

    public static void main(String[] args) {
        // 定义主线程池（用于接收客户端的连接，但是不做任何处理）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 定义主线程池（处理主线程池交过来的任务）
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // Netty服务启动的时候，从redis中查找是否有启动的实例，如果没有，使用默认端口号，如果有则在最大端口号累加10在启动
        Integer nettyPort = selectPort(nettyDefaultPort);

        try {
            // 构建Netty服务器
            ServerBootstrap server = new ServerBootstrap();     // 服务的启动类
            server.group(bossGroup, workerGroup)                // 把主从线程池放入到启动类中
                    .channel(NioServerSocketChannel.class)      // 设置Nio的双向通道
                    .childHandler(new WebsocketServerInitializer());                         // 设置处理器，用于处理workerGroup

            // 启动server，并且绑定端口号20976，同时启动方式为“同步”
            ChannelFuture channelFuture = server.bind(nettyPort).sync();

            // 监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // 优雅的关闭线程池组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
