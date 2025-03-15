package org.zhangruonan.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-18 21:39:47
 */
@Configuration
@Data
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.fileHost}")
    private String fileHost;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioUtils createMinioClient() {
        return new MinioUtils(endpoint, fileHost, bucketName, accessKey, secretKey);
    }

}
