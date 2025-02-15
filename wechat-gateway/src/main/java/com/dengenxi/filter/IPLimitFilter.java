package com.dengenxi.filter;

import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.grace.result.ResponseStatusEnum;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * .
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 13:06:52
 */
@Component
@Slf4j
public class IPLimitFilter extends BaseInfoProperties implements GlobalFilter, Ordered {

    /**
     * 需求：
     * 判断某个请求的ip在20秒内的请求次数是否超过3次
     * 如果超过3次，则限制访问30秒
     * 等待30秒静默后，才能够继续恢复访问
     */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("IP 当前的执行顺序order为1");
        if (true) {
            // 终止请求，返回错误信息
            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        }
        // 默认放行请求到后续的路由（服务）
        return chain.filter(exchange);
    }

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
    public Mono<Void> renderErrorMsg(ServerWebExchange exchange, ResponseStatusEnum statusEnum) {
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

    /**
     * 过滤器的顺序，数字越小优先级越大
     *
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 14:25:45
     */
    @Override
    public int getOrder() {
        return 1;
    }

}
