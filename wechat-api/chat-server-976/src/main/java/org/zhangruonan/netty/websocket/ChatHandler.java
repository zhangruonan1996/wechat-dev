package org.zhangruonan.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.zhangruonan.enums.MsgTypeEnum;
import org.zhangruonan.netty.ChatMsg;
import org.zhangruonan.netty.DataContent;
import org.zhangruonan.utils.JsonUtils;

import java.time.LocalDateTime;

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

    /**
     * 用于记录和管理所有客户端的channel组
     */
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        // 获得客户端传递过来的消息
        String content = textWebSocketFrame.text();
        System.out.println("接收到的数据：" + content);

        // 获取客户端发送过来的消息并解析
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        ChatMsg chatMsg = dataContent.getChatMsg();

        String msgText = chatMsg.getMsg();
        String receiverId = chatMsg.getReceiverId();
        String senderId = chatMsg.getSenderId();

        // 时间校准，以服务器的时间为准
        chatMsg.setChatTime(LocalDateTime.now());

        Integer msgType = chatMsg.getMsgType();

        // 获取channel
        Channel currentChannel = channelHandlerContext.channel();
        String currentChannelId = currentChannel.id().asLongText();
        String currentChannelShortId = currentChannel.id().asShortText();

        // System.out.println("客户端currentChannelId：" + currentChannelId);
        // System.out.println("客户端currentChannelShortId：" + currentChannelShortId);

        // 判断消息类型，根据不同的类型来处理不同的业务
        if (msgType == MsgTypeEnum.CONNECT_INIT.type) {
            // 当websocket初次open的时候，初始化channel，把channel和用户userid关联起来
            UserChannelSession.putMultiSession(senderId, currentChannel);
            UserChannelSession.putUserChannelIdRelation(currentChannelId, senderId);
        }

        UserChannelSession.outputMulti();

        // currentChannel.writeAndFlush(new TextWebSocketFrame(currentChannelId));

    }

    /**
     * 客户端连接到服务端之后（打开连接）
     *
     * @param ctx
     * @throws Exception
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-18 19:57:01
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("客户端建立连接，channel对应的长id为：" + currentChannelId);

        // 获得客户端的channel，并且存到ChannelGroup中进行管理（作为一个客户端群组）
        clients.add(currentChannel);
    }

    /**
     * 关闭连接，移除channel
     *
     * @param ctx
     * @throws Exception
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-18 19:59:54
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("客户端关闭连接，channel对应的长id为：" + currentChannelId);

        // 从ChannelGroup中移除对应的channel
        clients.remove(currentChannel);
    }

    /**
     * 发生异常并且捕获，移除channel
     *
     * @param ctx
     * @param cause
     * @throws Exception
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-18 20:00:15
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("服务端发生异常捕获，channel对应的长id为：" + currentChannelId);

        // 发生异常后关闭连接（关闭channel）
        currentChannel.close();

        // 从ChannelGroup中移除对应的channel
        clients.remove(currentChannel);
    }

}
