package org.zhangruonan.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 创建自定义助手类
 *
 * TextWebSocketFrame：用于为websocket专门处理的文本数据对象，Frame是数据（消息）的载体
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-17 21:53:07
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        // 获得客户端传递过来的消息
        String content = textWebSocketFrame.text();
        System.out.println("接收到的数据：" + content);

        // 获取channel
        Channel currentChannel = channelHandlerContext.channel();
        String currentChannelId = currentChannel.id().asLongText();
        String currentChannelShortId = currentChannel.id().asShortText();

        System.out.println("客户端currentChannelId：" + currentChannelId);
        System.out.println("客户端currentChannelShortId：" + currentChannelShortId);

        currentChannel.writeAndFlush(new TextWebSocketFrame(currentChannelId));

    }

}
