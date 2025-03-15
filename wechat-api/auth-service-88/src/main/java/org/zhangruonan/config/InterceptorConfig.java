package org.zhangruonan.config;

import org.zhangruonan.controller.interceptor.SmsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 19:03:34
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 在SpringBoot容器中放入拦截器
     *
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 19:04:00
     */
    @Bean
    public SmsInterceptor smsInterceptor() {
        return new SmsInterceptor();
    }

    /**
     * 注册拦截器，并且拦截指定的路由，否则不生效
     *
     * @param registry
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 19:04:33
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(smsInterceptor())
                .addPathPatterns("/passport/getSmsCode");
    }
}
