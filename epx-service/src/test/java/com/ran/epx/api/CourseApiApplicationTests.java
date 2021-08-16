package com.ran.epx.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.ran.epx.api.repository.CourseChapterRepository;
import com.ran.epx.api.service.CourseChapterService;
import com.ran.epx.model.ChapterContent;
import com.ran.epx.model.ContentProvider;
import com.ran.epx.model.CourseChapter;


@AutoConfigureMockMvc
@ContextConfiguration(classes = {CourseChapterService.class, CourseChapterRepository.class})
@WebMvcTest
class CourseApiApplicationTests {
	
	private static final String VIDEO_ID = "LmmwtquTpQs";

	@Autowired
	private CourseChapterService courseChapterService;
	
	@MockBean
    private CourseChapterRepository courseChapterRepository;
	
	@Test
	void testYoutubeUrlIdCreation_watch() {				
		CourseChapter courseChapter = new CourseChapter();
		courseChapter.setContentProvider(ContentProvider.YOUTUBE);
		ChapterContent content = new ChapterContent();		
		content.setYouTubeVideoLink("https://www.youtube.com/watch?v="+VIDEO_ID);
		courseChapter.setContent(content);		
		CourseChapter result = courseChapterService.createVideoLinkId(courseChapter);
		System.out.println(result.getContent());
		assertNotNull(result);
		assertNotNull(result.getContent());
		assertEquals(VIDEO_ID,result.getContent().getYouTubeVideoId());
	}
	@Test
	void testYoutubeUrlIdCreation_embed() {		
		CourseChapter courseChapter = new CourseChapter();
		ChapterContent content = new ChapterContent();
		courseChapter.setContentProvider(ContentProvider.YOUTUBE);
		content.setYouTubeVideoLink("https://www.youtube.com/embed/"+VIDEO_ID);
		courseChapter.setContent(content);		
		CourseChapter result = courseChapterService.createVideoLinkId(courseChapter);
		assertNotNull(result);
		assertNotNull(result.getContent());
		assertEquals(VIDEO_ID,result.getContent().getYouTubeVideoId());
	}

}
