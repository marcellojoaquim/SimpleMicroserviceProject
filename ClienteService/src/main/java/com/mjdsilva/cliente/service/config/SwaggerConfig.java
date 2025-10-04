package com.mjdsilva.cliente.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	
	@Bean
	OpenAPI customOpemApi() {
		return new OpenAPI()
				.info(new Info()
						.title("Service CLiente")
						.version("1.0")
						.contact(new Contact()
								.name("Marcello J")
								.email("marcellojaoquim1@hotmail.com")
								.url("https://github.com/marcellojoaquim"))
						.description("Microsservico de gerenciamento de clientes")
						.termsOfService("")
						.license(new License()
								.name("Apache 2.0")
								.url("")));
	}
}
