package com.github.ssw;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(){{
            setRequestFactory( new HttpComponentsClientHttpRequestFactory(HttpClientBuilder
                    .create()
                    .setConnectionManager(new PoolingHttpClientConnectionManager(){{
                    setDefaultMaxPerRoute(10);
                            setMaxTotal(30);
                    }}).build()));
        }};
    }
}
