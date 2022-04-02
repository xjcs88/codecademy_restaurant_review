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
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;


@Component
public class AdminReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");


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
            int peanutSum = 0;
            int peanutCount = 0;
            int eggSum = 0;
            int eggCount = 0;
            int dairySum = 0;
            int dairyCount = 0;

            for (Review r : reviews) {
                if (r.getPeanut() != null) {
                    peanutSum += r.getPeanut();
                    peanutCount += 1;
                }

                if (r.getEgg() != null) {
                    eggSum += r.getEgg();
                    eggCount += 1;
                }

                if (r.getDairy() != null) {
                    dairySum += r.getDairy();
                    dairyCount += 1;
                }
            }

            if (peanutCount > 0) {
                Double peanutScore = (double) peanutSum / peanutCount;
                restaurantToUpdate.setPeanut(Double.valueOf(decimalFormat.format(peanutScore)));
            }

            if (eggCount > 0) {
                Double eggScore = (double) eggSum / eggCount;
                restaurantToUpdate.setEgg(Double.valueOf(decimalFormat.format(eggScore)));
            }

            if (dairyCount > 0) {
                Double dairyScore = (double) dairySum / dairyCount;
                restaurantToUpdate.setDairy(Double.valueOf(decimalFormat.format(dairyScore)));
            }
        }
        return restaurantRepository.save(restaurantToUpdate);
    }
}
