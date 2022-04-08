package com.example.restaurant.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.example.restaurant.daos.User;
import com.example.restaurant.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Transactional
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;


    @Test
    void getAllUsers_whenUsersAreNotEmpty() throws Exception {
        User newUser1 = new User();
        User newUser2 = new User();
        newUser1.setName("Simon");
        newUser2.setName("Cissy");
        List<User> userList = new ArrayList<>();
        userList.add(newUser1);
        userList.add(newUser2);

        when(userRepository.findAll()).thenReturn(userList);

        List resultUserList = new ArrayList<User>();
        userService.getAllUsers().iterator().forEachRemaining(resultUserList::add);

        Assertions.assertTrue(resultUserList.containsAll(userList) && userList.containsAll(resultUserList));
    }

    @Test
    void getAllUsers_whenUsersAreEmpty() throws Exception {
        List<User> userList = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List resultUserList = new ArrayList<User>();
        userService.getAllUsers().iterator().forEachRemaining(resultUserList::add);

        Assertions.assertTrue(resultUserList.containsAll(userList) && userList.containsAll(resultUserList));
    }

    @Test
    void addUser_whenUsernameIsNotExisted() throws Exception {
        User newUser1 = new User();
        User newUser2 = new User();
        newUser1.setName("Simon");
        newUser2.setName("Cissy");
        List<User> userList = new ArrayList<>();
        userList.add(newUser1);
        userList.add(newUser2);

        when(userRepository.findAll()).thenReturn(userList);

        List resultUserList = new ArrayList<User>();
        userService.getAllUsers().iterator().forEachRemaining(resultUserList::add);

        Assertions.assertTrue(resultUserList.containsAll(userList) && userList.containsAll(resultUserList));
    }

    @Test
    void addUser_whenUsernameIsExisted_thenReturnResponseStatusException() throws Exception {
        User newUser = new User();
        newUser.setName("Simon");

        when(userRepository.findUserByName(anyString())).thenReturn(Optional.of(newUser));

        Assert.assertThrows(ResponseStatusException.class , () -> {userService.addUser(newUser);});
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
    void updateUserByName_whenUserIsFoundAndAllFieldsAreUpdated() throws Exception {
        User ExistedUser = new User();
        ExistedUser.setName("Cissy");

        User newUser = generateUser();

        when(userRepository.findUserByName(anyString())).thenReturn(Optional.of(newUser));

        Assertions.assertTrue(userService.updateUserByName(ExistedUser).equals(Optional.of(newUser)));
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    void updateUserByName_whenUserIsFoundAndAllFieldsAreNull() throws Exception {
        User ExistedUser = generateUser();

        User updateUser = new User();
        String updateUserName = RandomString.make(4);
        updateUser.setName(updateUserName);

        when(userRepository.findUserByName(anyString())).thenReturn(Optional.of(ExistedUser));
        Optional<User> updatedRandomUser = userService.updateUserByName(updateUser);
        Assertions.assertTrue(updatedRandomUser.equals(Optional.of(ExistedUser)));
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    void getUserByName_WhenNoUserFound() throws Exception{
        when(userRepository.findUserByName(anyString())).thenReturn(Optional.empty());

        Assert.assertThrows(ResponseStatusException.class, () -> userService.getUserByName(anyString()));
    }

    @Test
    void getUserByName_WhenUserFound() throws Exception{
        User user = generateUser();

        when(userRepository.findUserByName(anyString())).thenReturn(Optional.of(user));

        Assert.assertTrue(userService.getUserByName(anyString()).equals(Optional.of(user)));
    }

    @Test
    void isExistedByName_WhenUserFound() throws Exception{
        User user = generateUser();

        when(userRepository.findUserByName(anyString())).thenReturn(Optional.of(user));

        Assert.assertTrue(userService.isExistedByName(anyString()) == Optional.of(user).isPresent());
    }

    //helper method to generate a user with all fields having non-null values.
    static User generateUser(){
        User user = new User();
        user.setName(RandomString.make(4));
        user.setState(RandomString.make(4));
        user.setCity(RandomString.make(4));
        user.setZipCode(String.valueOf(new Random().nextInt(9000) + 1000));
        user.setCareDairy(false);
        user.setCarePeanut(false);
        user.setCareEgg(false);

        return user;
    }
}