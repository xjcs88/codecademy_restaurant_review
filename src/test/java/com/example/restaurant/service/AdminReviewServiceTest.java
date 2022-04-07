package com.example.restaurant.service;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.Status;
import com.example.restaurant.daos.User;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.ReviewRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Transactional
class AdminReviewServiceTest {
    @InjectMocks
    AdminReviewService adminReviewService;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    UserServiceTest userServiceTest;

    @Test
    void getAllReviewsByStatus() {
    }

    @Test
    void updatePendingReview() {
    }

    @Test
    void updateRestaurantScoresByRestaurantId_WhenRestaurantNotFound() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assert.assertThrows(ResponseStatusException.class , () -> {adminReviewService.updateRestaurantScoresByRestaurantId(anyLong());});
    }

    @Test
    void updateRestaurantScoresByRestaurantId_WhenRestaurantFoundWithNoReviews() throws Exception {
        Restaurant newRestaurant = generateRestaurant();
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(newRestaurant));

        when(reviewRepository.findReviewsByRestaurantIdAndStatus(newRestaurant.getId(), Status.ACCEPTED)).thenReturn(Collections.emptyList());
        Assertions.assertTrue(adminReviewService.updateRestaurantScoresByRestaurantId(newRestaurant.getId()).getEgg() == 0.0);
        Assertions.assertTrue(adminReviewService.updateRestaurantScoresByRestaurantId(newRestaurant.getId()).getPeanut() == 0.0);
        Assertions.assertTrue(adminReviewService.updateRestaurantScoresByRestaurantId(newRestaurant.getId()).getDairy() == 0.0);
    }

    @Test
    void updateRestaurantScoresByRestaurantId_WhenRestaurantFoundWithSomeReviews() throws Exception {
        Restaurant newRestaurant = generateRestaurant();
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(newRestaurant));
        Review review1 = new Review();
        review1.setDairy(5);
        review1.setEgg(3);
        Review review2 = new Review();
        review2.setEgg(4);
        review2.setPeanut(2);
        Review review3 = new Review();
        review3.setPeanut(3);
        review3.setDairy(4);
        review3.setEgg(1);
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1);
        reviewList.add(review2);
        reviewList.add(review3);
        when(reviewRepository.findReviewsByRestaurantIdAndStatus(newRestaurant.getId(), Status.ACCEPTED)).thenReturn(reviewList);
        Assertions.assertTrue(adminReviewService.updateRestaurantScoresByRestaurantId(newRestaurant.getId()).getEgg() == 2.67);
        Assertions.assertTrue(adminReviewService.updateRestaurantScoresByRestaurantId(newRestaurant.getId()).getPeanut() == 2.5);
        Assertions.assertTrue(adminReviewService.updateRestaurantScoresByRestaurantId(newRestaurant.getId()).getDairy() == 4.5);
    }

    //helper method to generate a restaurant with all fields having non-null values.
    static Restaurant generateRestaurant(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(RandomString.make(4));
        restaurant.setState(RandomString.make(4));
        restaurant.setCity(RandomString.make(4));
        restaurant.setZipCode(String.valueOf(new Random().nextInt(9000) + 1000));
        restaurant.setPeanut(0.0);
        restaurant.setEgg(0.0);
        restaurant.setDairy(0.0);
        restaurant.setPhone(RandomString.make(4));
        restaurant.setId(new Random().nextLong());
        return restaurant;
    }
}