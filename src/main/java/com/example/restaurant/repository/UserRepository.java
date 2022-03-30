package com.example.restaurant.repository;

import com.example.restaurant.daos.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
}