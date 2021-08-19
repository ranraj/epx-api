package com.ran.epx.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.ran.epx.api.repository.CourseChapterRepository;
import com.ran.epx.api.service.CourseChapterService;


@AutoConfigureMockMvc
@ContextConfiguration(classes = {CourseChapterService.class, CourseChapterRepository.class})
@WebMvcTest
class CourseApiApplicationTests {
	
	private static final String _1P3VCRHSYGO = "1p3vcRhsYGo";

	private static final String _6DWQZW0JJY = "6dwqZw0j_jY";

	private static final String YZK7NCVNBI = "yZ-K7nCVnBI";

	private static final String _0ZM3NAPSVMG = "0zM3nApSvMg";

	private static final String DQW4W9WGXCQ = "dQw4w9WgXcQ";

	private static final String VIDEO_ID = "LmmwtquTpQs";

	@Autowired
	private CourseChapterService courseChapterService;
	
	@MockBean
    private CourseChapterRepository courseChapterRepository;
	
	@Test
	void testYoutubeUrlIdCreation_watch() {		
		HashMap<String,String> testUrls =  new HashMap<>();
		testUrls.put("http://www.youtube.com/watch?v=0zM3nApSvMg&feature=feedrec_grec_index", _0ZM3NAPSVMG);
		testUrls.put("http://www.youtube.com/v/0zM3nApSvMg?fs=1&amp;hl=en_US&amp;rel=0", _0ZM3NAPSVMG);
		testUrls.put("http://www.youtube.com/watch?v=0zM3nApSvMg#t=0m10s", _0ZM3NAPSVMG);
		testUrls.put("http://www.youtube.com/embed/0zM3nApSvMg?rel=0", _0ZM3NAPSVMG);
		testUrls.put("http://www.youtube.com/watch?v=0zM3nApSvMg", _0ZM3NAPSVMG);
		testUrls.put("http://youtu.be/0zM3nApSvMg", _0ZM3NAPSVMG);
		testUrls.put("www.youtube-nocookie.com/embed/up_lNV-yoK4?rel=0", "up_lNV-yoK4");
		testUrls.put("http://www.youtube.com/user/Scobleizer#p/u/1/1p3vcRhsYGo", _1P3VCRHSYGO);
		testUrls.put("http://www.youtube.com/watch?v=cKZDdG9FTKY&feature=channel", "cKZDdG9FTKY");
		testUrls.put("http://www.youtube.com/watch?v=yZ-K7nCVnBI&playnext_from=TL&videos=osPknwzXEas&feature=sub", YZK7NCVNBI);
		testUrls.put("http://www.youtube.com/ytscreeningroom?v=NRHVzbJVx8", "NRHVzbJVx8");
		testUrls.put("http://www.youtube.com/user/SilkRoadTheatre#p/a/u/2/6dwqZw0j_jY", _6DWQZW0JJY);
		testUrls.put("http://youtu.be/6dwqZw0j_jY", _6DWQZW0JJY);
		testUrls.put("http://www.youtube.com/watch?v=6dwqZw0j_jY&feature=youtu.be", _6DWQZW0JJY);
		testUrls.put("http://youtu.be/afa-5HQHiAs", "afa-5HQHiAs");
		testUrls.put("http://www.youtube.com/user/Scobleizer#p/u/1/1p3vcRhsYGo?rel=0", _1P3VCRHSYGO);
		testUrls.put("http://www.youtube.com/watch?v=cKZDdG9FTKY&feature=channel", "cKZDdG9FTKY");
		testUrls.put("http://www.youtube.com/watch?v=yZ-K7nCVnBI&playnext_from=TL&videos=osPknwzXEas&feature=sub", YZK7NCVNBI);
		testUrls.put("http://www.youtube.com/ytscreeningroom?v=NRHVzbJVx8I", "NRHVzbJVx8I");
		testUrls.put("http://www.youtube.com/embed/nas1rJpm7wY?rel=0", "nas1rJpm7wY");
		testUrls.put("http://www.youtube.com/embed/nas1rJpm7wY?rel=0", "nas1rJpm7wY");
		testUrls.put("http://youtube.com/v/dQw4w9WgXcQ?feature=youtube_gdata_player", DQW4W9WGXCQ);
		testUrls.put("http://youtube.com/vi/dQw4w9WgXcQ?feature=youtube_gdata_player", DQW4W9WGXCQ);
		testUrls.put("http://youtube.com/?v=dQw4w9WgXcQ&feature=youtube_gdata_player", DQW4W9WGXCQ);
		testUrls.put("http://www.youtube.com/watch?v=dQw4w9WgXcQ&feature=youtube_gdata_player", DQW4W9WGXCQ);
		testUrls.put("http://youtube.com/?vi=dQw4w9WgXcQ&feature=youtube_gdata_player", DQW4W9WGXCQ);
		testUrls.put("http://youtube.com/watch?v=dQw4w9WgXcQ&feature=youtube_gdata_player", DQW4W9WGXCQ);
		testUrls.put("http://youtube.com/watch?vi=dQw4w9WgXcQ&feature=youtube_gdata_player", DQW4W9WGXCQ);
		testUrls.put("http://youtu.be/dQw4w9WgXcQ?feature=youtube_gdata_player", DQW4W9WGXCQ);
				
		for (Entry<String, String> entrySet  : testUrls.entrySet()) {
			System.out.println(entrySet.getKey());
			String result = courseChapterService.extractYoutubeVideoLinkId(entrySet.getKey());
			assertEquals(result, entrySet.getValue());
		}	
		
		
	}
	 

	//https://youtu.be/LmmwtquTpQs?t=547
		
}
