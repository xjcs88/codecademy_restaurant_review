package com.example.restaurant.controller;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;


    @GetMapping()
    public Iterable<Restaurant> getAllRestaurants() throws Exception {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurantById(@PathVariable Long id) throws Exception {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/search")
    public Iterable<Restaurant> getRestaurantsByZipCodeAndAllergy(@RequestParam String zipCode, @RequestParam String allergy) throws Exception{
        return restaurantService.getRestaurantsByZipCodeAndAllergy(zipCode, allergy);
    }

    @PostMapping()
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) throws Exception{
        return restaurantService.addRestaurant(restaurant);
    }

    /* For test purpose

    @DeleteMapping("all")
    public Iterable<Restaurant> deleteAllRestaurants() throws Exception{
        return restaurantService.deleteAllRestaurants();
    }
     */

    @PutMapping("/{id}")
    public Optional<Restaurant> updateById(@PathVariable("id") Long id, @RequestBody Restaurant restaurant) throws Exception{
        return restaurantService.updateRestaurant(id,restaurant);

    }
}