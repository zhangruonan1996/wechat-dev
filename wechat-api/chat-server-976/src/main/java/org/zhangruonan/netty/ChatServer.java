package org.zhangruonan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty服务启动类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-17 20:51:49
 */
public class ChatServer {

    public static void main(String[] args) {
        // 定义主线程池（用于接收客户端的连接，但是不做任何处理）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 定义主线程池（处理主线程池交过来的任务）
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 构建Netty服务器
            ServerBootstrap server = new ServerBootstrap();     // 服务的启动类
            server.group(bossGroup, workerGroup)                // 把主从线程池放入到启动类中
                    .channel(NioServerSocketChannel.class)      // 设置Nio的双向通道
                    .childHandler(null);                         // 设置处理器，用于处理workerGroup

            // 启动server，并且绑定端口号20976，同时启动方式为“同步”
            ChannelFuture channelFuture = server.bind(20976).sync();

            // 监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // 优雅的关闭线程池组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
