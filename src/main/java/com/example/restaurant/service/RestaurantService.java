package com.example.restaurant.service;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class RestaurantService
{
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Iterable<Restaurant> getAllRestaurants() throws Exception
    {
        return restaurantRepository.findAll();
    }

    public Restaurant addRestaurant(Restaurant restaurant) throws Exception
    {
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public Iterable<Restaurant> deleteAllRestaurants() throws Exception
    {
        Iterable<Restaurant> restaurants = restaurantRepository.findAll();
        restaurantRepository.deleteAll();
        return restaurants;
    }

    public Optional<Restaurant> updateRestaurant(Long id, Restaurant restaurant) throws Exception
    {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (!optionalRestaurant.isPresent()) {
            return null;
        }

        Restaurant restaurantToUpdate = optionalRestaurant.get();
        if (restaurant.getAddress() != null) {
            restaurantToUpdate.setAddress(restaurant.getAddress());
        }

        if (restaurant.getName() != null) {
            restaurantToUpdate.setName(restaurant.getName());
        }

        if (restaurant.getPhone() != null) {
            restaurantToUpdate.setPhone(restaurant.getPhone());
        }

        if (restaurant.getPeanut() != null) {
            restaurantToUpdate.setPeanut(restaurant.getPeanut());
        }

        if (restaurant.getEgg() != null) {
            restaurantToUpdate.setEgg(restaurant.getEgg());
        }

        if (restaurant.getDairy() != null) {
            restaurantToUpdate.setDairy(restaurant.getDairy());
        }

        return Optional.of(restaurantToUpdate);
    }

    public Optional<Restaurant> getRestaurant(Long id)
    {
        return restaurantRepository.findById(id);
    }
}