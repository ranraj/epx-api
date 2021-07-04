package com.cisco.epx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.epx.api.model.Course;
import com.cisco.epx.api.repository.CourseRepository;

@RestController
@RequestMapping("/course")
public class CourseController {
      

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Object> getAllCourses() {    	
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addCourse(@RequestBody Course course){
    	courseRepository.save(course);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody Course course){
    	Course response = courseRepository.save(course);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @DeleteMapping("/{tripId}")
//    public ResponseEntity<Object> deleteTrip(@PathVariable("tripId") String id) throws ExecutionException, InterruptedException {
//        DocumentSnapshot documentSnapshot = dbFirestore.collection(COURSE_COLLECTION_NAME).document(id).get().get();
//
//        if (!documentSnapshot.exists()) {
//            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
//        }
//
//        DocumentReference documentReference = dbFirestore.collection(COURSE_COLLECTION_NAME).document(id);
//        documentReference.delete().get();
//        return new ResponseEntity<>(id, HttpStatus.OK);
//
//    }
//
//    @GetMapping("/others/{ownerId}")
//    public ResponseEntity<Object> fetchOtherTrips(@PathVariable("ownerId") String ownerId) throws ExecutionException, InterruptedException {
//
//        List<QueryDocumentSnapshot> queryDocumentSnapshots = dbFirestore.collection(COURSE_COLLECTION_NAME)
//                .whereNotEqualTo("ownerId", ownerId).get().get().getDocuments();
//
//        List<Course> courses = queryDocumentSnapshots.stream()
//                .map(QueryDocumentSnapshot::getData)
//                .map(Course::toCourse)
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(courses, HttpStatus.OK);
//    }
//
//    @PostMapping("/like")
//    public ResponseEntity<Object> likeTrip(@RequestBody LikeCourseDto likeTripDto) throws ExecutionException, InterruptedException {
//
//        DocumentSnapshot documentTripSnapshot = dbFirestore.collection(COURSE_COLLECTION_NAME).document(likeTripDto.getCourseId()).get().get();
//
//        if (!documentTripSnapshot.exists()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        DocumentSnapshot documentUserSnapshot = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId()).get().get();
//
//        if (!documentUserSnapshot.exists()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        Course trip = Course.toCourse(Objects.requireNonNull(documentTripSnapshot.getData()));
//        List<String> alreadyLike = trip.getLikedBy();
//        User user = User.toUser(Objects.requireNonNull(documentUserSnapshot.getData()));
//        if (alreadyLike.contains(user.getEmail())) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        alreadyLike.add(user.getEmail());
//        trip.setLikedBy(alreadyLike);
//
//        DocumentReference tripRef = dbFirestore.collection(COURSE_COLLECTION_NAME).document(trip.getId());
//        tripRef.update(trip.generateMap()).get();
//
//        DocumentReference documentReference = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId());
//        List<String> userFavorites = user.getFavorites();
//        userFavorites.add(likeTripDto.getCourseId());
//        user.setFavorites(userFavorites);
//        documentReference.update(user.generateMap()).get();
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }        
//
//    @PostMapping("/unlike")
//    public ResponseEntity<Object> unlikeTrip(@RequestBody LikeCourseDto likeTripDto) throws ExecutionException, InterruptedException {
//
//        DocumentSnapshot documentTripSnapshot = dbFirestore.collection(COURSE_COLLECTION_NAME).document(likeTripDto.getCourseId()).get().get();
//
//        if (!documentTripSnapshot.exists()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        DocumentSnapshot documentUserSnapshot = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId()).get().get();
//
//        if (!documentUserSnapshot.exists()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        Course trip = Course.toCourse(Objects.requireNonNull(documentTripSnapshot.getData()));
//        List<String> alreadyLike = trip.getLikedBy();
//        User user = User.toUser(Objects.requireNonNull(documentUserSnapshot.getData()));
//        if (!alreadyLike.contains(user.getEmail())) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        alreadyLike.remove(user.getEmail());
//        trip.setLikedBy(alreadyLike);
//
//        DocumentReference tripRef = dbFirestore.collection(COURSE_COLLECTION_NAME).document(trip.getId());
//        tripRef.update(trip.generateMap()).get();
//
//        DocumentReference documentReference = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId());
//        List<String> userFavorites = user.getFavorites();
//        userFavorites.remove(likeTripDto.getCourseId());
//        user.setFavorites(userFavorites);
//        documentReference.update(user.generateMap()).get();
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
