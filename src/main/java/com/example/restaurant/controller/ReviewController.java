package com.example.restaurant.controller;

import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.Status;
import com.example.restaurant.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/add")
    public Review addReview(@RequestBody Review review) throws Exception{
        return reviewService.addReview(review);
    }

//    @GetMapping("/allreviews")
//    public Iterable<Review> getAllReviews() throws Exception{
//        return reviewService.getAllReviews();
//    }

    @GetMapping("/all")
    public Iterable<Review> getAllReviewsByStatus(@RequestParam(required = false) Status status) throws Exception{
        return reviewService.getAllReviewsByStatus(status);
    }

//    @GetMapping("/all")
//    public Iterable<Review> getAllReviewsByRestaurantId(@PathVariable Long id) throws Exception{
//        return reviewService.getReviewsByRestaurantId(id);
//    }

    @PutMapping("/{id}/update")
    public Optional<Review> adminUpdateReviewById(@RequestBody Review review) throws Exception{
        return reviewService.adminUpdateReviewByStatus(review);
    }

    @GetMapping("/{id}/all")
    public Iterable<Review> getAllReviewsByRestaurantIdAndStatus(@RequestBody Review review) throws Exception{
        return reviewService.getAllReviewsByRestaurantIdAndStatus(review.getRestaurantId(), Status.ACCEPTED);
    }
}