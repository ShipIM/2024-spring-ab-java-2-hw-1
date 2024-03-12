package com.example.homework.config.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String url;

    private Integer port;

    private String accessKey;

    private String secretKey;

    private Boolean secure;

    private String bucket;

    private Long imageSize;

}
