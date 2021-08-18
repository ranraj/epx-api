package com.ran.epx.api.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ran.epx.api.repository.CourseChapterRepository;
import com.ran.epx.model.ChapterContent;
import com.ran.epx.model.ContentProvider;
import com.ran.epx.model.CourseChapter;

@Service
public class CourseChapterService {

	private static final String YOUTU_REGEX = "^.*(youtu\\.be\\/|vi?\\/|u\\/\\w\\/|embed\\/|\\?vi?=|\\&vi?=)([^#\\&\\?]*).*";
	private static final String WATCH = "watch";
	private static final String EMBED = "embed";
	private static final String YOUTUBE = "youtube";
	@Autowired
	private CourseChapterRepository courseChapterRepository;

	public CourseChapter addChapter(CourseChapter courseChapter) {
		CourseChapter chapter = updateVideoLinkId(courseChapter);
		chapter = courseChapterRepository.save(chapter);
		return chapter;
	}

	public CourseChapter updateChapter(CourseChapter courseChapter) {
		CourseChapter chapter = updateVideoLinkId(courseChapter);
		chapter = courseChapterRepository.save(chapter);
		return chapter;

	}

	private CourseChapter updateVideoLinkId(CourseChapter courseChapter) {
		ContentProvider contentProvider = courseChapter.getContentProvider();
		if (contentProvider == ContentProvider.YOUTUBE && courseChapter.getContent() != null) {

			ChapterContent content = courseChapter.getContent();
			String videoId = extractYoutubeVideoLinkId(content.getYouTubeVideoLink());
			content.setYouTubeVideoId(videoId);

		}
		return courseChapter;
	}

	public String extractYoutubeVideoLinkId(String videoLink) {
		
		Pattern compiledPattern = Pattern.compile(YOUTU_REGEX);
		Matcher matcher = compiledPattern.matcher(videoLink);
		
		if (matcher.find()) {
			System.out.println(matcher.group(2));
			return matcher.group(2);

		}
		return null;

	}

}
