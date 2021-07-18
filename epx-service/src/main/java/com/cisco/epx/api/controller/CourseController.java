package com.ran.epx.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ran.epx.api.dto.LikeCourseDto;
import com.ran.epx.api.repository.CourseChapterRepository;
import com.ran.epx.api.repository.CourseRepository;
import com.ran.epx.api.repository.UserRepository;
import com.ran.epx.model.Course;
import com.ran.epx.model.CourseChapter;
import com.ran.epx.model.User;


@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseChapterRepository courseChapterRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<Object> getAllCourses() {
		return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> addCourse(@RequestBody Course course) {
		courseRepository.save(course);

		return new ResponseEntity<>(course, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Object> updateCourse(@RequestBody Course course) {
		Course response = courseRepository.save(course);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<Course> getCourse(@PathVariable("courseId") String courseId) {
		Optional<Course> course = courseRepository.findById(courseId);
		return course.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Object> deleteCourse(@PathVariable("courseId") String courseId) {
		courseRepository.deleteById(courseId);
		return new ResponseEntity<>(courseId, HttpStatus.OK);

	}

	@GetMapping("/others/{ownerId}")
	public ResponseEntity<Object> fetchOtherCourse(@PathVariable("ownerId") String ownerId) {

		return new ResponseEntity<>(courseRepository.findCourseByOwnerId(ownerId), HttpStatus.OK);
	}

	@PostMapping("/like")
	public ResponseEntity<Object> likeCourse(@RequestBody LikeCourseDto likeCourseDto) {

		Optional<Course> courseOpt = courseRepository.findById(likeCourseDto.getCourseId());
		if (courseOpt.isPresent()) {
			Course course = courseOpt.get();
			List<String> alreadyLike = course.getLikedBy();
			if (alreadyLike == null) {
				alreadyLike = new ArrayList<>();
			}
			Optional<User> userOpt = userRepository.findById(likeCourseDto.getUserId());
			if (userOpt.isPresent()) {
				User user = userOpt.get();
				if (alreadyLike.contains(user.getId())) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				alreadyLike.add(user.getId());
				course.setLikedBy(alreadyLike);
				courseRepository.save(course);
			}
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/unlike")
	public ResponseEntity<Object> unlikeCourse(@RequestBody LikeCourseDto likeCourseDto) {
		Optional<Course> courseOpt = courseRepository.findById(likeCourseDto.getCourseId());
		if (courseOpt.isPresent()) {
			Course course = courseOpt.get();
			List<String> alreadyLike = course.getLikedBy();
			if (alreadyLike == null) {
				alreadyLike = new ArrayList<>();
			}
			Optional<User> userOpt = userRepository.findById(likeCourseDto.getUserId());
			if (userOpt.isPresent()) {
				User user = userOpt.get();
				if (alreadyLike.contains(user.getId())) {
					alreadyLike.remove(user.getId());
				}
				course.setLikedBy(alreadyLike);
				courseRepository.save(course);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/{courseId}/chapters")
	public ResponseEntity<Object> saveChapter(@PathVariable("courseId") String courseId,@RequestBody CourseChapter chapter) {
		if(!courseRepository.findById(courseId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		chapter.setCourseId(courseId);
		return new ResponseEntity<>(courseChapterRepository.save(chapter), HttpStatus.OK);
	}
	
	@GetMapping("/{courseId}/chapters")
	public ResponseEntity<Object> getAllChapters(@PathVariable("courseId") String courseId) {
		return new ResponseEntity<>(courseChapterRepository.findByCourseId(courseId), HttpStatus.OK);
	}
	
	@GetMapping("/{courseId}/chapters/{chapterId}")
	public ResponseEntity<Object> getChapterId(@PathVariable("courseId") String courseId,@PathVariable("chapterId") String chapterId) {
		
		return new ResponseEntity<>(courseChapterRepository.findByCourseIdAndChapterId(courseId,chapterId), HttpStatus.OK);
	}
	@DeleteMapping("/{courseId}/chapters/{chapterId}")
	public ResponseEntity<Object> deleteChapterId(@PathVariable("courseId") String courseId,@PathVariable("chapterId") String chapterId) {
		if(!courseRepository.findById(courseId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		courseChapterRepository.deleteById(chapterId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
