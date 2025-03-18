package org.zhangruonan.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-18 20:06:44
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataContent {

    // private Integer action;  // 动作类型
    /**
     * 用户的聊天内容entity
     */
    private ChatMsg chatMsg;

    /**
     * 格式化后的聊天时间
     */
    private String chatTime;

    /**
     * 扩展字段
     */
    private String extend;

    private Object serverNode;

}
