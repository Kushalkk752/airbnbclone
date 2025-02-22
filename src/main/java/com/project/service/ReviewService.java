package com.project.service;

import com.project.entity.AppUser;
import com.project.entity.Property;
import com.project.entity.Review;
import com.project.payload.ReviewDto;
import com.project.repository.PropertyRepository;
import com.project.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }


    public ReviewDto createReview(ReviewDto dto) {
        Review review = mapToEntity(dto);
        Review save = reviewRepository.save(review);
        ReviewDto reviewDto = mapToDto(save);
        return reviewDto;
    }

    private ReviewDto mapToDto(Review save) {
        ReviewDto reviewDto = modelMapper.map(save, ReviewDto.class);
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto dto) {
        Review review = modelMapper.map(dto, Review.class);
        return review;
    }

    public List<Review> getAllReviews(AppUser user) {
        List<Review> reviews = reviewRepository.findByAppUser(user);
        return reviews;
    }
}
