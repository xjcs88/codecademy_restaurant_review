package com.example.restaurant.repository;

import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Optional<Review> findByStatus(Enum status);
}
