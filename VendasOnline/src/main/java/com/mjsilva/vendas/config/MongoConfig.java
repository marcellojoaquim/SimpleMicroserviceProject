package com.mjsilva.vendas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.mjsilva.vendas.repository")
public class MongoConfig {

}
