package com.example.restaurant.service;

import com.example.restaurant.daos.*;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.ReviewRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

    @Test
    void getAllReviewsByStatus_WhenStatusIsNull() throws Exception{
        Review review1 = generateReview();
        Review review2 = generateReview();
        List<Review> reviewList = new ArrayList<Review>();
        reviewList.add(review1);
        reviewList.add(review2);
        when(reviewRepository.findAll()).thenReturn(reviewList);
        Assert.assertTrue(adminReviewService.getAllReviewsByStatus(null) == reviewList);
    }

    @Test
    void getAllReviewsByStatus_WhenStatusIsInvalid() throws Exception{
        Exception exception = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status not found!");
        Assert.assertThrows(exception.getClass(), () -> {adminReviewService.getAllReviewsByStatus("HELLO");});
        Assert.assertTrue(exception.getMessage().contains("Status not found!"));
        Assert.assertTrue(HttpStatus.BAD_REQUEST == ((ResponseStatusException) exception).getStatus());
    }

    @Test
    void getAllReviewsByStatus_WhenStatusIsNotNull() throws Exception{
        Review review1 = generateReview();
        review1.setStatus(Status.PENDING);
        Review review2 = generateReview();
        review1.setStatus(Status.ACCEPTED);
        Review review3 = generateReview();
        review1.setStatus(Status.REJECTED);
        List<Review> allReviewList = new ArrayList<Review>();
        allReviewList.add(review1);
        allReviewList.add(review2);
        List<Review> pendingReviewList = new ArrayList<Review>();
        pendingReviewList.add(review1);
        List<Review> rejectedReviewList = new ArrayList<Review>();
        rejectedReviewList.add(review3);

        when(reviewRepository.findAllByStatus(Status.PENDING)).thenReturn(pendingReviewList);
        when(reviewRepository.findAllByStatus(Status.REJECTED)).thenReturn(rejectedReviewList);

        Assert.assertTrue(adminReviewService.getAllReviewsByStatus("PENDING").equals(pendingReviewList));
        Assert.assertTrue(adminReviewService.getAllReviewsByStatus("REJECTED").equals(rejectedReviewList));
    }

    @Test
    void updatePendingReview_WhenReviewNotExisted() throws Exception{
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assert.assertThrows(ResponseStatusException.class , () -> {adminReviewService.updatePendingReview(anyLong(), new AdminReview());});
    }

    @Test
    void updatePendingReview_WhenReviewExisted() throws Exception{
        Restaurant restaurant = generateRestaurant();
        AdminReview adminReview1 = new AdminReview();
        adminReview1.setAcceptReview(true);
        Review review1 = generateReview();
        AdminReview adminReview2 = new AdminReview();
        adminReview2.setAcceptReview(false);
        Review review2 = generateReview();

        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review1));
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(reviewRepository.findReviewsByRestaurantIdAndStatus(review1.getRestaurantId(), Status.ACCEPTED)).thenReturn(Collections.emptyList());

        Assert.assertTrue(adminReviewService.updatePendingReview(review1.getRestaurantId(), adminReview1).get().getStatus().equals(Status.ACCEPTED));
        Mockito.verify(reviewRepository, Mockito.times(1)).save(any());
        Assert.assertTrue(adminReviewService.updatePendingReview(review2.getRestaurantId(), adminReview2).get().getStatus().equals(Status.REJECTED));
        Mockito.verify(reviewRepository, Mockito.times(2)).save(any());
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

        Review review1 = generateReview();
        review1.setDairy(5);
        review1.setEgg(3);
        review1.setPeanut(null);
        Review review2 = generateReview();
        review2.setDairy(null);
        review2.setEgg(4);
        review2.setPeanut(2);
        Review review3 = generateReview();
        review3.setPeanut(3);
        review3.setDairy(4);
        review3.setEgg(1);
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1);
        reviewList.add(review2);
        reviewList.add(review3);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(newRestaurant));
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

    static Review generateReview(){
        Review review = new Review();
        review.setName(RandomString.make(4));
        review.setStatus(Status.PENDING);
        review.setId(new Random().nextLong());
        review.setCommentary(RandomString.make(4));
        review.setRestaurantId(new Random().nextLong());
        review.setEgg(new Random().nextInt(6));
        review.setPeanut(new Random().nextInt(6));
        review.setDairy(new Random().nextInt(6));
        return review;
    }
}