package com.ran.epx.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ran.epx.api.dto.TextSimilarityScore;


public class EpxTextSimilarityService implements TextSimilarityService {
	private static final Logger logger = LoggerFactory.getLogger(EpxTextSimilarityService.class);
	
	@Value("${text.similarity.service.url}")
	private String textSimilarityServiceUrl;		  

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public TextSimilarityScore getTextSimilarity(String text1, String text2, String lang) {
		
		try {
			String url = String.format("%s?text1={q}&text2={q}", textSimilarityServiceUrl);

			ResponseEntity<TextSimilarityScore> response = restTemplate.getForEntity(url, TextSimilarityScore.class,
					text1, text2);			
			return response.getBody();
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return null;
	}

}
