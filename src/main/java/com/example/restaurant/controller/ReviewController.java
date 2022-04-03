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


    @PostMapping()
    public Review addReview(@RequestBody Review review) throws Exception{
        return reviewService.addReview(review);
    }

//    Below functions are moved to AdminReviewController.

//    @GetMapping("/all")
//    public Iterable<Review> getAllReviewsByStatus(@RequestParam(required = false) Status status) throws Exception{
//        return reviewService.getAllReviewsByStatus(status);
//    }
//
//    @PutMapping("/{id}/update")
//    public Optional<Review> adminUpdateReviewById(@RequestBody Review review) throws Exception{
//        return reviewService.adminUpdateReviewByStatus(review);
//    }
//
//    @GetMapping("/{id}/all")
//    public Iterable<Review> getAllReviewsByRestaurantIdAndStatus(@PathVariable Long id, String status) throws Exception{
//        return reviewService.getAllReviewsByRestaurantIdAndStatus(id, status);
//    }
}
