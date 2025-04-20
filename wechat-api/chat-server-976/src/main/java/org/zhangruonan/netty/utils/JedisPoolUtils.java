package org.zhangruonan.netty.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * Jedis连接池工具类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 19:55:50
 */
public class JedisPoolUtils {

    private static final JedisPool jedisPool;

    static {
        // 配置连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数
        jedisPoolConfig.setMaxTotal(10);
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(10);
        // 最小空闲数
        jedisPoolConfig.setMinIdle(5);
        // 最长等待时间，单位：毫秒
        jedisPoolConfig.setMaxWait(Duration.ofMillis(1500));

        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000, "123456");
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}
