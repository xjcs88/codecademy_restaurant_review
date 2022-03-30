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

    @GetMapping("/all")
    public Optional<Review> getReviewByStatus(@RequestParam(required = false) Status status) throws Exception{
        return reviewService.getReviewByStatus(status);
    }
}
