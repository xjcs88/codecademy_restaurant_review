package com.example.restaurant.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.restaurant.daos.Restaurant;

import java.util.Optional;


public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{
    Optional<Restaurant> findRestaurantsByNameAndZipCode(String name, String zipCode);
}

