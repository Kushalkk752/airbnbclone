package com.project.controller;


import com.project.entity.AppUser;
import com.project.entity.Property;
import com.project.entity.Review;
import com.project.payload.ReviewDto;
import com.project.repository.PropertyRepository;
import com.project.repository.ReviewRepository;
import com.project.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewService reviewService;
    private PropertyRepository propertyRepository;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, PropertyRepository propertyRepository,
                            ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> writeReview(
            @RequestBody ReviewDto dto,
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user){
        Property property = propertyRepository.findById(propertyId).get();
        if(reviewRepository.existsByAppUserAndProperty(user,property)){
            return new ResponseEntity<>("User has already given the review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        dto.setProperty(property);
        dto.setAppUser(user);
        ReviewDto reviewDto = reviewService.createReview(dto);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @GetMapping("/user/review")
    public ResponseEntity<List<Review>> getUserReviews(
            @AuthenticationPrincipal AppUser user
    ){
        List<Review> reviews = reviewService.getAllReviews(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }



}
