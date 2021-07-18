package com.ran.epx.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoRepositories(basePackages = "com.ran.epx.api.repository")
@Configuration
@EnableReactiveMongoRepositories(basePackages="com.ran.epx.api.repository")
public class MongoDBConfig {
   
}
