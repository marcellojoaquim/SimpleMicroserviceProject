package com.mjsilva.vendas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTamplateConfig {

    @Bean
    RestTemplate restTamplate() {
		return new RestTemplate();
	}
}
