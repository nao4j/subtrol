package com.nao4j.subtrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@EnableScheduling
public class ContextConfiguration {

    @Bean
    public RestTemplate restTemplate(final List<HttpMessageConverter<?>> converters) {
        return new RestTemplate(converters);
    }

}
