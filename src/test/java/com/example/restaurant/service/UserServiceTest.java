package com.example.restaurant.service;

import com.example.restaurant.daos.User;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.UserRepository;
import org.junit.Assert;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//@SpringBootTest
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
//    @Resource
//    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private UserService userService;

    @Test
    void getAllUsers() throws Exception {
        User newUser1 = new User();
        User newUser2 = new User();
        newUser1.setName("Simon");
        newUser2.setName("Cissy");
        List<User> userList = new ArrayList<>();
        userList.add(newUser1);
        userList.add(newUser2);
        userRepository.save(newUser1);
        userRepository.save(newUser2);
        Mockito.when(userService.getAllUsers()).thenReturn(userList);
        List<User> resultList = new ArrayList<>();
        userService.getAllUsers().forEach(u -> {resultList.add(u);});
        Assert.assertArrayEquals(resultList.toArray(), userList.toArray());
    }

    @Test
    void addUser() {
    }

    @Test
    void updateUserByName() {
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