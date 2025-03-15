package org.zhangruonan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-18 20:19:44
 */
@Configuration
public class StaticResourceConfig extends WebMvcConfigurationSupport {

    /**
     * 添加静态资源映射路径
     *
     * @param registry
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 20:20:24
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:D:\\data\\upload\\");
        super.addResourceHandlers(registry);
    }
}
