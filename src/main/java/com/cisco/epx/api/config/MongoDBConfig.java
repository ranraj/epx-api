package com.cisco.epx.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.cisco.epx.api.repository")
@Configuration
public class MongoDBConfig {
   
}
