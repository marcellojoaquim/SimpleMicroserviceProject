package com.mjsilva.vendas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.Validator;

@Configuration
public class ValidatorConfig {
	
	@Bean
	Validator validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		return bean;
	}
}
