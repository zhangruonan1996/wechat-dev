package com.dengenxi.utils;

import cn.hutool.core.util.IdUtil;

/**
 * 封装hutool雪花算法
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 21:38:56
 */
public class SnowUtils {

    private static long dataCenterId = 1;  //数据中心
    private static long workerId = 1;     //机器标识

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
    }

}
