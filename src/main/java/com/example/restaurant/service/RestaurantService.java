package com.example.restaurant.service;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

public Iterable<Restaurant> getAllRestaurants() throws Exception{
return restaurantRepository.findAll();
}

public Optional<Restaurant> getRestaurantById(Long id) throws Exception{
    Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
    if (optionalRestaurant.isEmpty()){
        return null;
    }

    return optionalRestaurant;
}

@ResponseStatus(HttpStatus.CREATED)
public Restaurant addRestaurant(Restaurant restaurant) throws Exception{
    Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantsByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
    if (optionalRestaurant.isEmpty()){
        restaurantRepository.save(restaurant);
        return restaurant;
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
}

public Iterable<Restaurant> deleteAllRestaurants() throws Exception{
    Iterable<Restaurant> restaurants = restaurantRepository.findAll();
    restaurantRepository.deleteAll();
    return restaurants;
    }

    public Optional<Restaurant> updateRestaurant(Long id, Restaurant restaurant) throws Exception{
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isEmpty()){
            return null;
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