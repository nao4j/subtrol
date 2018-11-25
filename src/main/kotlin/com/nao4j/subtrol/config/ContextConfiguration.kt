package com.nao4j.subtrol.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestTemplate

@Configuration
@EnableScheduling
class ContextConfiguration {

    @Bean
    fun restTemplate(converters: List<HttpMessageConverter<*>>): RestTemplate = RestTemplate(converters)

}
