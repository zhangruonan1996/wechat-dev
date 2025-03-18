package org.zhangruonan.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * websocket初始化器
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-17 21:44:14
 */
public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 通过SocketChannel获得对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // ============= 通过管道添加handler处理器 =============

        // websocket基于http协议，所以需要有http的编解码器
        pipeline.addLast(new HttpServerCodec());

        // 添加对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // 对httpMessage进行聚合，聚合称位FullHttpRequest或FullHttpResponse
        // 几乎在netty的编程中，都会使用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // ============= 以上是用于支持http协议相关的handler =============

        // ============= 增加心跳支持 start =============

        // 针对客户端，如果在1分钟内没有向服务端发送读写心跳（ALL），则主动断开连接
        // 如果是读空闲或者写空闲，则不做任何处理
        pipeline.addLast(new IdleStateHandler(8, 10, 30 * 60));

        pipeline.addLast(new HeartBeatHandle());

        // ============= 增加心跳支持 end =============

        // ============= 以下是用于支持websocket =============

        /**
         * WebSocket 服务器处理的协议，用于指定给客户端连接的时候访问路由：/ws
         * 此 Handler 会帮我们处理一些比较复杂的繁重的操作
         * 会处理一些握手操作：handShaking（close，ping，pong）  ping + pong = 心跳
         * 对于 WebSocket 来说，数据都是以frames进行传输的，不同的数据类型所对应的frames也都不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 添加自定义的助手类
        pipeline.addLast(new ChatHandler());

    }
}
