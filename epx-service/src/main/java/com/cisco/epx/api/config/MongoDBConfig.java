package com.cisco.epx.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoRepositories(basePackages = "com.cisco.epx.api.repository")
@Configuration
@EnableReactiveMongoRepositories(basePackages="com.cisco.epx.api.repository")
public class MongoDBConfig {
   
}
