package com.example.restaurant.service;

import com.example.restaurant.daos.AdminReview;
import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.Status;
import com.example.restaurant.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Component
public class AdminReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Iterable<Review> getAllReviewsByStatus(String status) throws Exception{
        if(status != null){
            Status reviewStatus = Status.PENDING;
            try{
                reviewStatus = Status.valueOf(status);
            }
            catch (IllegalArgumentException exception){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status not found!");
            }
            return reviewRepository.findAllByStatus(reviewStatus);
        }

        return reviewRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Review> updatePendingReview(Long id, AdminReview adminReview) throws Exception{
            Optional<Review> optionalReview = reviewRepository.findById(id);
            if(optionalReview.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found!");
            }
            Review reviewToUpdate = optionalReview.get();
            if (adminReview.isAcceptReview()) {
                reviewToUpdate.setStatus(Status.ACCEPTED);
            }
            else{
                reviewToUpdate.setStatus(Status.REJECTED);
            }
            reviewRepository.save(reviewToUpdate);
            return optionalReview;

    }

    public Double updateRestaurantScoresByRestaurantId(Long restaurantId) throws Exception{
        List<Review> reviews = reviewRepository.findReviewsByRestaurantIdAndStatus(restaurantId, Status.ACCEPTED);
        if(!reviews.isEmpty()){
            Double peanutScore = null;
            Double eggScore = null;
            Double dairyScore = null;
            for(int i = 0; i < reviews.size(); i++){
                if(reviews.get(i).getPeanut().)
            }
        }
    }
}
