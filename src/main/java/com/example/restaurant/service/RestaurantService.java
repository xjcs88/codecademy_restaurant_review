package com.example.restaurant.service;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    private final Pattern zipCodePattern = Pattern.compile("\\d{4}");

public Iterable<Restaurant> getAllRestaurants() throws Exception{
return restaurantRepository.findAll();
}

public Optional<Restaurant> getRestaurantById(Long id) throws Exception{
    Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
    if (optionalRestaurant.isEmpty()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found!");
    }

    return optionalRestaurant;
}

public Iterable<Restaurant> getRestaurantsByZipCodeAndAllergy(String zipCode, String allergy) throws Exception {
    if(!zipCodePattern.matcher(zipCode).matches()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zipcode should be 4 digits!");
    }
    Iterable<Restaurant> restaurants;

    if (allergy.equalsIgnoreCase("peanut")) {
        restaurants = restaurantRepository.findRestaurantsByZipCodeAndPeanutNotNullOrderByPeanut(zipCode);
    }
    else if (allergy.equalsIgnoreCase("egg")) {
        restaurants = restaurantRepository.findRestaurantsByZipCodeAndEggNotNullOrderByEgg(zipCode);
    }
    else if(allergy.equalsIgnoreCase("dairy")){
        restaurants = restaurantRepository.findRestaurantsByZipCodeAndDairyNotNullOrderByDairy(zipCode);
    }
    else{
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid scores found!");
    }
    return restaurants;
}

public Restaurant addRestaurant(Restaurant restaurant) throws Exception{
    Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantsByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
    if (optionalRestaurant.isEmpty()){
        restaurantRepository.save(restaurant);
        return restaurant;
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This restaurant already exists!");
}

public Iterable<Restaurant> deleteAllRestaurants() throws Exception{
    Iterable<Restaurant> restaurants = restaurantRepository.findAll();
    restaurantRepository.deleteAll();
    return restaurants;
    }

public Optional<Restaurant> updateRestaurant(Long id, Restaurant restaurant) throws Exception{
    Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
    if (optionalRestaurant.isEmpty()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "restaurant not found!");
    }

    Restaurant restaurantToUpdate = optionalRestaurant.get();
    if(restaurant.getCity() != null){
        restaurantToUpdate.setCity(restaurant.getCity());
    }

    if(restaurant.getState() != null){
        restaurantToUpdate.setState(restaurant.getState());
    }

    if(restaurant.getZipCode() != null){
        restaurantToUpdate.setZipCode(restaurant.getZipCode());
    }

    if(restaurant.getName() != null){
        restaurantToUpdate.setName(restaurant.getName());
    }

    if(restaurant.getPhone() != null){
        restaurantToUpdate.setPhone(restaurant.getPhone());
    }

    if(restaurant.getPeanut() != null){
        restaurantToUpdate.setPeanut(restaurant.getPeanut());
    }

    if(restaurant.getEgg() != null){
        restaurantToUpdate.setEgg(restaurant.getEgg());
    }

    if(restaurant.getDairy() != null){
        restaurantToUpdate.setDairy(restaurant.getDairy());
    }

    return Optional.of(restaurantToUpdate);
}
}