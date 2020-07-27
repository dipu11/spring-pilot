package com.example.module.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by DIPU on 7/27/20
 */
@Configuration
public class MinioConfig {
    @Value("${minio.access.name}")
    String accessKey;
    @Value("${minio.access.secret}")
    String accessSecret;
    @Value("${minio.url}")
    String minioUrl;


    @Bean
    public MinioClient generateMinioClient() {
        try {
            MinioClient client =
                    MinioClient.builder()
                            .endpoint(minioUrl)
                            .credentials(accessKey, accessSecret)
                            .build();
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
