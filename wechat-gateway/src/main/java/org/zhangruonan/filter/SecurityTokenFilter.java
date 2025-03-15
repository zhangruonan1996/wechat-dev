package org.zhangruonan.filter;

import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.utils.RenderErrorUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-17 20:28:00
 */
@Component
@Slf4j
public class SecurityTokenFilter extends BaseInfoProperties implements GlobalFilter, Ordered  {

    @Resource
    private ExcludeUrlPathProperties excludeUrlPathProperties;

    // 路径匹配规则器
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取当前用户请求的路径url
        String url = exchange.getRequest().getURI().getPath();
        log.info("SecurityTokenFilter url: {}", url);

        // 获得所有的需要排除校验的url
        List<String> excludeList = excludeUrlPathProperties.getUrls();

        // 校验并且排除excludeList
        if (excludeList != null && !excludeList.isEmpty()) {
            for (String excludeUrl : excludeList) {
                if (antPathMatcher.matchStart(excludeUrl, url)) {
                    // 如果匹配，表示当前的url不需要进行拦截，直接放行
                    return chain.filter(exchange);
                }
            }
        }

        // 排除静态资源服务static
        String fileStart = excludeUrlPathProperties.getFileStart();
        if (StringUtils.isNotBlank(fileStart)) {
            boolean matchFileStart = antPathMatcher.matchStart(fileStart, url);
            if (matchFileStart) {
                return chain.filter(exchange);
            }
        }

        // 请求被拦截，需要校验
        log.info("当前请求的路径：【{}】被拦截", url);

        // 从请求头中获取用户id和用户token
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String userId = headers.getFirst(HEADER_USER_ID);
        String userToken = headers.getFirst(HEADER_USER_TOKEN);
        log.info("userId：{}", userId);
        log.info("userToken: {}", userToken);

        // 判断header中是否有token，对用户请求进行拦截判断
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String redisToken = redis.get(REDIS_USER_TOKEN + ":" + userId);
            // 匹配则放行
            if (redisToken.equals(userToken)) {
                return chain.filter(exchange);
            }
        }

        return RenderErrorUtils.display(exchange, ResponseStatusEnum.UN_LOGIN);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
