package com.example.restaurant.controller;

import com.example.restaurant.daos.AdminReview;
import com.example.restaurant.daos.Review;
import com.example.restaurant.service.AdminReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminReviewController {

    @Autowired
    private AdminReviewService adminReviewService;

    @GetMapping("/reviews")
    public Iterable<Review> getAllReviewsByStatus(@RequestParam(required = false) String status) throws Exception{
        return adminReviewService.getAllReviewsByStatus(status);
    }

    @PutMapping("/reviews/{id}")
    public Optional<Review> updatePendingReview(@PathVariable Long id, @RequestBody AdminReview adminReview) throws Exception{
        return adminReviewService.updatePendingReview(id, adminReview);
    }

}
