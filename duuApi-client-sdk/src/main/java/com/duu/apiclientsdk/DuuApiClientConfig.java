package com.duu.apiclientsdk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author : duu
 * @data : 2023/11/22
 * @from ：https://github.com/0oHo0
 **/
@Data
@ConfigurationProperties("duu-api.client")
@ComponentScan
@Configuration
public class DuuApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public DuuApiClient duuApiClient(){
        return new DuuApiClient(accessKey,secretKey);
    }
}

