package com.dengenxi.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-17 20:41:36
 */
@Component
@Data
@PropertySource("classpath:exclude-url-path.properties")
@ConfigurationProperties(prefix = "exclude")
public class ExcludeUrlPathProperties {

    private List<String> urls;

    private String fileStart;

}
