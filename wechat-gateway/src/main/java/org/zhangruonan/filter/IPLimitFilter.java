package org.zhangruonan.filter;

import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.utils.IPUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
@RefreshScope
public class IPLimitFilter extends BaseInfoProperties implements GlobalFilter, Ordered {

    /**
     * 需求：
     * 判断某个请求的ip在20秒内的请求次数是否超过3次
     * 如果超过3次，则限制访问30秒
     * 等待30秒静默后，才能够继续恢复访问
     */

    @Value("${blackIP.CONTINUE_COUNTS}")
    private Integer CONTINUE_COUNTS;
    @Value("${blackIP.TIME_INTERVAL}")
    private Integer TIME_INTERVAL;
    @Value("${blackIP.LIMIT_TIMES}")
    private Integer LIMIT_TIMES;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // if (true) {
        //     // 终止请求，返回错误信息
        //     return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        // }

        // log.info("CONTINUE_COUNTS:{}", CONTINUE_COUNTS);
        // log.info("TIME_INTERVAL:{}", TIME_INTERVAL);
        // log.info("LIMIT_TIMES:{}", LIMIT_TIMES);

        return doLimit(exchange, chain);

        // 默认放行请求到后续的路由（服务）
        // return chain.filter(exchange);
    }

    /**
     * 限制IP请求次数的判断
     *
     * @param exchange
     * @param chain
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 14:46:06
     */
    public Mono<Void> doLimit(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 根据request获取请求IP
        ServerHttpRequest request =  exchange.getRequest();
        String ip = IPUtil.getIP(request);

        // 正常的ip定义
        final String ipRedisKey = "gateway-ip:" + ip;
        // 被拦截的黑名单IP，如果在redis中存在，则表示目前被关小黑屋
        final String ipRedisLimitKey = "gateway-ip:limit" + ip;

        // 获得当前的IP并且查询还剩下多少时间，如果时间存在（大于0），则表示当前仍然处在黑名单中
        long limitLeftTimes = redis.ttl(ipRedisLimitKey);
        if (limitLeftTimes > 0) {
            // 终止请求，返回错误
            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        }

        // 在redis中获得IP的累加次数
        long requestCounts = redis.increment(ipRedisKey, 1);
        // 如果是第一次访问，也就是从0开始计数，则初次访问就是1
        if (requestCounts == 1) {
            redis.expire(ipRedisKey, TIME_INTERVAL);
        }
        // 如果还能获得请求的正常次数，说明用户的连续请求落在限定的次数之类
        // 一旦请求次数超过限定的连续访问次数，则需要限制当前的IP
        if (requestCounts > CONTINUE_COUNTS) {
            // 限制IP访问的时间
            redis.set(ipRedisLimitKey, ipRedisLimitKey, LIMIT_TIMES);
            // 终止请求，返回错误
            return renderErrorMsg(exchange, ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP);
        }
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
