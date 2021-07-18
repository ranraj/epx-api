package com.ran.epx.api.service;

import com.ran.epx.api.dto.TextSimilarityScore;

public class TextSimilarityDelegator {

	private TextSimilarityService textSimilarityService;

	public TextSimilarityDelegator(TextSimilarityService textSimilarityService) {
		this.textSimilarityService = textSimilarityService;
	}

	public TextSimilarityScore getTextSimilarity(String text1, String text2, String lang) {
		return textSimilarityService.getTextSimilarity(text1, text2, lang);
	}

}
