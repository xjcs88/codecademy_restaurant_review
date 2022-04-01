package com.example.restaurant.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.restaurant.daos.Restaurant;

import java.util.List;
import java.util.Optional;


public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{
    Optional<Restaurant> findRestaurantsByNameAndZipCode(String name, String zipCode);
    List<Restaurant> findRestaurantsByZipCodeAndPeanutNotNullOrderByPeanut(String zipCode);
    List<Restaurant> findRestaurantsByZipCodeAndEggNotNullOrderByEgg(String zipCode);
    List<Restaurant> findRestaurantsByZipCodeAndDairyNotNullOrderByDairy(String zipCode);
}

