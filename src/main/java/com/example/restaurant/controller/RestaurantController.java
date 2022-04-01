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

//    @Autowired
//    private final RestaurantRepository restaurantRepository;
//
//    public MainController(RestaurantRepository restaurantRepository) {
//        this.restaurantRepository = restaurantRepository;
//        //this.restaurantService = restaurantService;
//    }

    @GetMapping("/all")
    public Iterable<Restaurant> getAllRestaurants() throws Exception {
        Iterable<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return restaurants;
    }

    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurantById(@PathVariable Long id) throws Exception {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/find")
    public Iterable<Restaurant> getRestaurantsByZipCodeAndAllergy(@RequestParam String zipCode, @RequestParam String allergy) throws Exception{
        return restaurantService.getRestaurantsByZipCodeAndAllergy(zipCode, allergy);
    }

    @PostMapping("/add")
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) throws Exception{
        return restaurantService.addRestaurant(restaurant);
    }

    @DeleteMapping("all")
    public Iterable<Restaurant> deleteAllRestaurants() throws Exception{
        Iterable<Restaurant> restaurants = restaurantService.deleteAllRestaurants();
        return restaurants;
    }

    @PutMapping("/{id}")
    public Optional<Restaurant> updateById(@PathVariable("id") Long id, @RequestBody Restaurant restaurant) throws Exception{
        return restaurantService.updateRestaurant(id,restaurant);

    }
}