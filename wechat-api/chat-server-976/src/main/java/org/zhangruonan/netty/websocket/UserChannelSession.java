package org.zhangruonan.netty.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会话管理
 * 用户id和channel的关联处理
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-18 20:19:08
 */
public class UserChannelSession {

    /**
     * 用于多端同时接收消息，允许同一个账号在多个设备同时在线，例如iPad/iPhone/Mac等设备同时收到消息
     *
     * key: userId， value: 多个用户的channel
     */
    private static Map<String, List<Channel>> multiSession = new HashMap<>();

    /**
     * 用于记录用户id和客户端channel长id的关联关系
     */
    private static Map<String, String> userChannelIdRelation = new HashMap<>();

    /**
     * 添加channelId与用户id的关联关系
     *
     * @param channelId
     * @param userId
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-18 20:24:36
     */
    public static void putUserChannelIdRelation(String channelId, String userId) {
        userChannelIdRelation.put(channelId, userId);
    }

    /**
     * 根据channelId获取用户id
     *
     * @param channelId channelId
     * @return 用户id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-18 20:25:11
     */
    public static String getUserIdByChannelId(String channelId) {
        return userChannelIdRelation.get(channelId);
    }

    /**
     * 根据用户id查询用户所有channel
     *
     * @param userId
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-18 20:26:28
     */
    public static List<Channel> getMultiChannels(String userId) {
        return multiSession.get(userId);
    }

    public static void removeMultiChannels(String userId, String channelId) {
        List<Channel> channels = getMultiChannels(userId);
        if (channels == null || channels.isEmpty()) {
            return;
        }

        for (int i = 0; i < channels.size(); i++) {
            Channel tempChannel = channels.get(i);
            if (tempChannel.id().asLongText().equals(channelId)) {
                channels.remove(i);
            }
        }

        multiSession.put(userId, channels);
    }

    /**
     * 添加用户关联的channel
     *
     * @param userId 用户id
     * @param channel 用户channel
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-18 20:27:41
     */
    public static void putMultiSession(String userId, Channel channel) {
        List<Channel> channels = getMultiChannels(userId);
        if (channels == null || channels.size() == 0) {
            channels = new ArrayList<>();
        }
        channels.add(channel);

        multiSession.put(userId, channels);
    }

    public static void outputMulti() {
        System.out.println("++++++++++++++++++++++++++++");

        for (Map.Entry<String, List<Channel>> entry : multiSession.entrySet()) {
            System.out.println("--------------------------------");

            System.out.println("UserId: " + entry.getKey());
            List<Channel> temp = entry.getValue();
            for (Channel channel : temp) {
                System.out.println("\t\t ChannelId: " + channel.id().asLongText());
            }

            System.out.println("--------------------------------");
        }

        System.out.println("++++++++++++++++++++++++++++");
    }

}
