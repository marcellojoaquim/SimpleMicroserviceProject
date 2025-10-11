package com.mjsilva.vendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@RefreshScope
@EnableFeignClients
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class VendasOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasOnlineApplication.class, args);
	}

}
