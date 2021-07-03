package com.cisco.epx.api.controller;

import static com.cisco.epx.api.controller.UserController.USER_COLLECTION_NAME;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.epx.api.dto.LikeCourseDto;
import com.cisco.epx.api.model.Course;
import com.cisco.epx.api.model.User;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

@RestController
@RequestMapping("/course")
public class CourseController {

    public static final String COURSE_COLLECTION_NAME = "course";
      

    @Autowired
    private Firestore dbFirestore;

    @GetMapping
    public ResponseEntity<Object> getAllCourses() {

        CollectionReference coursesCollection = dbFirestore.collection(COURSE_COLLECTION_NAME);
        return new ResponseEntity<>(coursesCollection.listDocuments(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addCourse(@RequestBody Course course) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = dbFirestore.collection(COURSE_COLLECTION_NAME).document();

        course.setId(documentReference.getId());
        documentReference.set(course.toMap()).get();

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody Course course) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = dbFirestore.collection(COURSE_COLLECTION_NAME).document(course.getId()).get().get();

        if (!documentSnapshot.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DocumentReference documentReference = dbFirestore.collection(COURSE_COLLECTION_NAME).document(course.getId());
        documentReference.update(course.generateMap()).get();
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Object> deleteTrip(@PathVariable("tripId") String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = dbFirestore.collection(COURSE_COLLECTION_NAME).document(id).get().get();

        if (!documentSnapshot.exists()) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }

        DocumentReference documentReference = dbFirestore.collection(COURSE_COLLECTION_NAME).document(id);
        documentReference.delete().get();
        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    @GetMapping("/others/{ownerId}")
    public ResponseEntity<Object> fetchOtherTrips(@PathVariable("ownerId") String ownerId) throws ExecutionException, InterruptedException {

        List<QueryDocumentSnapshot> queryDocumentSnapshots = dbFirestore.collection(COURSE_COLLECTION_NAME)
                .whereNotEqualTo("ownerId", ownerId).get().get().getDocuments();

        List<Course> courses = queryDocumentSnapshots.stream()
                .map(QueryDocumentSnapshot::getData)
                .map(Course::toCourse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<Object> likeTrip(@RequestBody LikeCourseDto likeTripDto) throws ExecutionException, InterruptedException {

        DocumentSnapshot documentTripSnapshot = dbFirestore.collection(COURSE_COLLECTION_NAME).document(likeTripDto.getCourseId()).get().get();

        if (!documentTripSnapshot.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DocumentSnapshot documentUserSnapshot = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId()).get().get();

        if (!documentUserSnapshot.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Course trip = Course.toCourse(Objects.requireNonNull(documentTripSnapshot.getData()));
        List<String> alreadyLike = trip.getLikedBy();
        User user = User.toUser(Objects.requireNonNull(documentUserSnapshot.getData()));
        if (alreadyLike.contains(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        alreadyLike.add(user.getEmail());
        trip.setLikedBy(alreadyLike);

        DocumentReference tripRef = dbFirestore.collection(COURSE_COLLECTION_NAME).document(trip.getId());
        tripRef.update(trip.generateMap()).get();

        DocumentReference documentReference = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId());
        List<String> userFavorites = user.getFavorites();
        userFavorites.add(likeTripDto.getCourseId());
        user.setFavorites(userFavorites);
        documentReference.update(user.generateMap()).get();

        return new ResponseEntity<>(HttpStatus.OK);
    }        

    @PostMapping("/unlike")
    public ResponseEntity<Object> unlikeTrip(@RequestBody LikeCourseDto likeTripDto) throws ExecutionException, InterruptedException {

        DocumentSnapshot documentTripSnapshot = dbFirestore.collection(COURSE_COLLECTION_NAME).document(likeTripDto.getCourseId()).get().get();

        if (!documentTripSnapshot.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DocumentSnapshot documentUserSnapshot = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId()).get().get();

        if (!documentUserSnapshot.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Course trip = Course.toCourse(Objects.requireNonNull(documentTripSnapshot.getData()));
        List<String> alreadyLike = trip.getLikedBy();
        User user = User.toUser(Objects.requireNonNull(documentUserSnapshot.getData()));
        if (!alreadyLike.contains(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        alreadyLike.remove(user.getEmail());
        trip.setLikedBy(alreadyLike);

        DocumentReference tripRef = dbFirestore.collection(COURSE_COLLECTION_NAME).document(trip.getId());
        tripRef.update(trip.generateMap()).get();

        DocumentReference documentReference = dbFirestore.collection(USER_COLLECTION_NAME).document(likeTripDto.getUserId());
        List<String> userFavorites = user.getFavorites();
        userFavorites.remove(likeTripDto.getCourseId());
        user.setFavorites(userFavorites);
        documentReference.update(user.generateMap()).get();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
