package com.example.restaurant.service;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.User;
import com.example.restaurant.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;

    public Review addReview(Review review) throws Exception{
        Optional<User> optionalUser = userService.getUserByName(review.getName());
        Optional<Restaurant> optionalRestaurant = restaurantService.getRestaurantById(review.getId());
        if (!optionalRestaurant.isPresent() || !optionalUser.isPresent()){
            return null;
        }
        reviewRepository.save(review);
        return review;
    }

    public Optional<Review> getReviewByStatus(Enum status) throws Exception{
        Optional<Review> statusReviews = reviewRepository.findByStatus(status);
        return statusReviews;
    }
}
