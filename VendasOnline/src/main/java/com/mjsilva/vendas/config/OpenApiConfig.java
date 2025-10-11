package com.mjsilva.vendas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenApi(@Value("${application-version}") String appVersion) {
		return new OpenAPI()
				.info(new Info()
						.title("Servico Vendas")
						.version(appVersion)
						.description("Microservico de vendas")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org"))
						.contact(new Contact().name("Marcello Joaquim").email("mjsilva@email.com")));
	}
}
