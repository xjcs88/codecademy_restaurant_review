package com.example.restaurant.service;

import com.example.restaurant.daos.AdminReview;
import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.Status;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;


@Component
public class AdminReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;


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

            updateRestaurantScoresByRestaurantId(reviewToUpdate.getRestaurantId());
            return optionalReview;

    }

    public Restaurant updateRestaurantScoresByRestaurantId(Long restaurantId) throws Exception {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found!");
        }
        Restaurant restaurantToUpdate = optionalRestaurant.get();

        List<Review> reviews = reviewRepository.findReviewsByRestaurantIdAndStatus(restaurantId, Status.ACCEPTED);
        if (!reviews.isEmpty()) {
            BigDecimal peanutScore = BigDecimal.valueOf(0);
            Integer peanutTotalScore = 0;
            Integer peanutTotalReviews = 0;
            BigDecimal eggScore = BigDecimal.valueOf(0);
            Integer eggTotalScore = 0;
            Integer eggTotalReviews = 0;
            BigDecimal dairyScore = BigDecimal.valueOf(0);
            Integer dairyTotalScore = 0;
            Integer dairyTotalReviews = 0;
            for (int i = 0; i < reviews.size(); i++) {
                if (reviews.get(i).getPeanut() != null) {
                    if(peanutScore == null){
                        peanutScore = BigDecimal.valueOf(0);
                    }
                    peanutTotalScore += reviews.get(i).getPeanut();
                    peanutTotalReviews += 1;
                }

                if (reviews.get(i).getEgg() != null) {
                    if(eggScore == null){
                        eggScore = BigDecimal.valueOf(0);
                    }
                    eggTotalScore += reviews.get(i).getEgg();
                    eggTotalReviews += 1;
                }

                if (reviews.get(i).getPeanut() != null) {
                    if(dairyScore == null){
                        dairyScore = BigDecimal.valueOf(0);
                    }
                    dairyTotalScore += reviews.get(i).getDairy();
                    dairyTotalReviews += 1;
                }
                if (peanutTotalReviews > 0) {
                    peanutScore = new BigDecimal(peanutTotalScore).divide(new BigDecimal(peanutTotalReviews), 2, RoundingMode.HALF_UP);
                }

                if (eggTotalReviews > 0) {
                    eggScore = new BigDecimal(eggTotalScore).divide(new BigDecimal(eggTotalReviews), 2, RoundingMode.HALF_UP);
                }

                if (dairyTotalReviews > 0) {
                    dairyScore = new BigDecimal(dairyTotalScore).divide(new BigDecimal(dairyTotalReviews), 2, RoundingMode.HALF_UP);
                }
            }
            if (peanutScore != null) {
                restaurantToUpdate.setPeanut(peanutScore.doubleValue());
            }

            if (eggScore != null) {
                restaurantToUpdate.setEgg(eggScore.doubleValue());
            }

            if (dairyScore != null) {
                restaurantToUpdate.setDairy(dairyScore.doubleValue());
            }
        }
        return restaurantRepository.save(restaurantToUpdate);
    }
}
