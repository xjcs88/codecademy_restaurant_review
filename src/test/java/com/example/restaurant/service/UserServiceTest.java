package com.example.restaurant.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.restaurant.RestaurantApplication;
import com.example.restaurant.daos.User;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
//@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
//@WebAppConfiguration
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {
//    @Resource
//    private UserService userService;
//    @Autowired
//    private MockMvc mockMvc;

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Test
    void getAllUsers() throws Exception {
        User newUser1 = new User();
        User newUser2 = new User();
        newUser1.setName("Simon");
        newUser2.setName("Cissy");
        List<User> userList = new ArrayList<>();
        userList.add(newUser1);
        userList.add(newUser2);

        when(userRepository.findAll()).thenReturn(userList);

        var result = userService.getAllUsers();
        var userIterator = result.iterator();

        var resultUserList = new ArrayList<User>();

        while (userIterator.hasNext()){
            var user = userIterator.next();
            resultUserList.add(user);
        }

        Assertions.assertTrue(resultUserList.size()==userList.size());
    }

    @Test
    void addUser() {
    }

    @Test
    void updateUserByName_whenUserIsNotFound_thenReturnResponseStatusException() throws Exception {
        Optional<User> empty = Optional.empty();
        when(userRepository.findUserByName(anyString())).thenReturn(empty);

        var user = new User();
        user.setName("Cissy");

        Assert.assertThrows(ResponseStatusException.class , () -> {userService.updateUserByName(user);});
        Mockito.verify(userRepository, Mockito.times(0)).save(any());
    }

    @Test
    void getUserByName() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void isExistedByName() {
    }


}