package com.example.restaurant.service;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.Status;
import com.example.restaurant.daos.User;
import com.example.restaurant.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;

    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(Review review) throws Exception{
        Optional<User> optionalUser = userService.getUserByName(review.getName());
        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(review.getRestaurantId());
        if (optionalRestaurant.isEmpty() || optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        reviewRepository.save(review);
        return review;
    }

    public Iterable<Review> getAllReviewsByStatus(Enum status) throws Exception{
        if(status != null){
        Iterable<Review> statusReviews = reviewRepository.findAllByStatus(status);
        return statusReviews;
        }
        Iterable<Review> reviews = reviewRepository.findAll();
        return reviews;
        }


//    public Iterable<Review> getAllReviews() throws Exception{
//        Iterable<Review> statusReviews = reviewRepository.findAll();
//        return statusReviews;
//    }

    public List<Review> getAllReviewsByRestaurantIdAndStatus(Long id, String status) throws Exception{
        Status findStatus = Status.PENDING;
        try{
            findStatus = Status.valueOf(status);
        }
        catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Review> restaurants = reviewRepository.findReviewsByRestaurantIdAndStatus(id, findStatus);
        return restaurants;
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Review> adminUpdateReviewByStatus(Review review) throws Exception{
        Optional<Review> optionalReview = reviewRepository.findById(review.getId());
        if(optionalReview.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Review reviewToUpdate = optionalReview.get();
        reviewToUpdate.setStatus(review.getStatus());
        reviewRepository.save(reviewToUpdate);
        return optionalReview;
    }

}
