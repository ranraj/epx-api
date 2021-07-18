package com.ran.epx.api.service;

import com.ran.epx.api.dto.TextSimilarityScore;

public interface TextSimilarityService {
	public TextSimilarityScore getTextSimilarity(String text1,String text2, String lang);
}
