package com.example.restaurant.repository;

import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.Status;
import com.example.restaurant.daos.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Iterable<Review> findAllByStatus(Enum status);
    List<Review> findReviewsByRestaurantIdAndStatus(Long restaurantId, Status status);
}
