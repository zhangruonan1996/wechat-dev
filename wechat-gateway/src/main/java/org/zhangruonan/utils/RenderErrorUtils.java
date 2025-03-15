package org.zhangruonan.utils;

import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import com.google.gson.Gson;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-17 21:45:32
 */
public class RenderErrorUtils extends BaseInfoProperties {

    /**
     * 重新包装并返回错误信息
     *
     * @param exchange
     * @param statusEnum
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 14:30:59
     */
    public static Mono<Void> display(ServerWebExchange exchange, ResponseStatusEnum statusEnum) {
        // 1. 获取相应的response
        ServerHttpResponse response = exchange.getResponse();
        // 2. 构建jsonResult
        GraceJSONResult jsonResult = GraceJSONResult.exception(statusEnum);
        // 3. 设置header类型
        if (!response.getHeaders().containsKey("Content-Type")) {
            response.getHeaders().add("Content-Type", MimeTypeUtils.APPLICATION_JSON_VALUE);
        }
        // 4. 修改response状态码为500
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        // 5. 转换json并且向response写入数据
        String resultJson = new Gson().toJson(jsonResult);
        DataBuffer buffer = response.bufferFactory().wrap(resultJson.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
