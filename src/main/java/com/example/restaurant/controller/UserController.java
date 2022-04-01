package com.example.restaurant.controller;

import com.example.restaurant.daos.Review;
import com.example.restaurant.daos.User;
import com.example.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Iterable<User> getAllUsers() throws Exception{
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) throws Exception{
        return userService.addUser(user);
    }

    @PutMapping("/{name}")
    public Optional<User> updateByName(@PathVariable String name, @RequestBody User user) throws Exception{
        return userService.updateUserByName(user);
    }

    @GetMapping("/{name}")
    public Optional<User> getByName(@PathVariable String name) throws Exception{
        return userService.getUserByName(name);
    }

    @GetMapping("/{name}/exist")
    public Boolean isExistedByName(@RequestBody Review review) throws Exception{
        return userService.isExistedByName(review.getName());
    }

    @DeleteMapping("/all")
    public Iterable<User> deleteAll() throws Exception{
        return userService.deleteAll();
    }
}
