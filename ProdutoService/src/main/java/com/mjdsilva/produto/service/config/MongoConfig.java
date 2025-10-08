package com.mjdsilva.produto.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.mjdsilva.produto.service.repository")
public class MongoConfig {

}
