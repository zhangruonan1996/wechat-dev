package org.zhangruonan.utils;// package org.wechat.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 18:14:22
 */
@Component
@Data
@PropertySource("classpath:tencentCloud.properties")
@ConfigurationProperties(prefix = "tencent.cloud")
public class TencentCloudProperties {

    private String SecretId;
    private String SecretKey;

}
