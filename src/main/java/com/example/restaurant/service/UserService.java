package com.example.restaurant.service;

import com.example.restaurant.daos.Restaurant;
import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.User;
import com.example.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserRepository userRepository;


    public Iterable<User> getAllUsers() throws Exception{
        return userRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(User user) throws Exception{
        if(this.isExistedByName(user.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User name exists!");
        }
        userRepository.save(user);
        return user;
    }

    public Optional<User> updateUserByName(User user) throws Exception{
        Optional<User> optionalUser = userRepository.findByName(user.getName());
        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        User userToUpdate = optionalUser.get();
        if(user.getName() != null){
            userToUpdate.setName(user.getName());
        }

        if(user.getCity() != null){
            userToUpdate.setName(user.getCity());
        }

        if(user.getId() != null){
            userToUpdate.setId(user.getId());
        }

        if(user.getState() != null){
            userToUpdate.setState(user.getState());
        }

        if(user.getZipCode() != null){
            userToUpdate.setZipCode(user.getZipCode());
        }

        if(user.getCareDairy() != null){
            userToUpdate.setCareDairy(user.getCareDairy());
        }

        if(user.getCareEgg() != null){
            userToUpdate.setCareEgg(user.getCareEgg());
        }

        if(user.getCarePeanut() != null){
            userToUpdate.setCarePeanut(user.getCarePeanut());
        }

        return Optional.of(userToUpdate);
    }

    public Optional<User> getUserByName(String name) throws Exception{
        Optional<User> optionalUser = userRepository.findByName(name);
        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        return optionalUser;
    }

    /* For test purpose

    public Iterable<User> deleteAll() throws Exception{
        Iterable<User> users = userRepository.findAll();
        userRepository.deleteAll();
        return users;
    }

     */

    public Boolean isExistedByName(String name) throws Exception{
        Optional<User> optionalUser = userRepository.findByName(name);
        return optionalUser.isPresent();
    }
}
