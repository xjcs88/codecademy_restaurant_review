package com.example.restaurant.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.restaurant.daos.Restaurant;


public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{

}

