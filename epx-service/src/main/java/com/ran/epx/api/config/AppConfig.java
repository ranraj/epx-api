package com.ran.epx.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ran.epx.api.service.DandelionTextSimilarityService;
import com.ran.epx.api.service.EpxTextSimilarityService;
import com.ran.epx.api.service.TextSimilarityService;

@Configuration
public class AppConfig {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean 
	@ConditionalOnProperty(name = "text.similarity.service.provider", havingValue = "dandelion")    
    public TextSimilarityService dandelionTextSimilarityService() {
         return new DandelionTextSimilarityService();
    }
	
	@Bean
	@ConditionalOnProperty(name = "text.similarity.service.provider", havingValue = "epx")     
    public TextSimilarityService epxTextSimilarityService() {
         return new EpxTextSimilarityService();
    }
	
}
