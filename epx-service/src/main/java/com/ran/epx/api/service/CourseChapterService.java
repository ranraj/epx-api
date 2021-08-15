package com.ran.epx.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ran.epx.api.repository.CourseChapterRepository;
import com.ran.epx.model.ChapterContent;
import com.ran.epx.model.ContentProvider;
import com.ran.epx.model.CourseChapter;

@Service
public class CourseChapterService {

	private static final String WATCH = "watch";
	private static final String EMBED = "embed";
	private static final String YOUTUBE = "youtube";
	@Autowired
	private CourseChapterRepository courseChapterRepository;

	public CourseChapter addChapter(CourseChapter courseChapter) {
		CourseChapter chapter =  createVideoLinkId(courseChapter);
		chapter = courseChapterRepository.save(chapter);
		return chapter;
	}
	
	public CourseChapter updateChapter(CourseChapter courseChapter) {
		CourseChapter chapter =  createVideoLinkId(courseChapter);
		chapter = courseChapterRepository.save(chapter);
		return chapter;
	}

	private CourseChapter createVideoLinkId(CourseChapter courseChapter) {
		ContentProvider contentProvider = courseChapter.getContentProvider();

		if (contentProvider == ContentProvider.YOUTUBE) {

			ChapterContent content = courseChapter.getContent();
		
			String videoLink = content.getYouTubeVideoLink();
			
			if (!videoLink.contains(YOUTUBE)) {
				throw new IllegalArgumentException("Not an youtube link");
			}

			if (videoLink.contains(EMBED)) {
				String[] linksPart = videoLink.split("/");
				content.setYouTubeVideoId(linksPart[linksPart.length - 1]);
			}
			if (videoLink.contains(WATCH)) {
				String[] linksPart = videoLink.split("=");				
				content.setYouTubeVideoId(linksPart[linksPart.length - 1]);			
			}
			courseChapter.setContent(content);
			// Moved to Chapter content
			courseChapter.setVideoLink(null);
		}
		return courseChapter;
	}
}
