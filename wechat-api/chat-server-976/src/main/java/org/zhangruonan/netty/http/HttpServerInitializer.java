package org.zhangruonan.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel注册后，会执行里面相应的初始化方法
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-17 21:04:39
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 通过SocketChannel获得对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 通过管道，添加handler处理器
        // HttpServerCodec 是由Netty自己提供的助手类，此处可以理解为管道中的拦截器
        // 当请求到服务端，我们需要进行解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 添加自定义的助手类，当请求访问，返回“hello zrn”
        pipeline.addLast("HttpHandler", new HttpHandler());

    }
}
