package com.tweetApp.service;

import com.tweetApp.document.Users;
import com.tweetApp.model.UserStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserStatus registerUser(Users user);

    List<Users> findAllUsers();

    UserDetails findUserByLoginId(String loginId, String enteredPassword);

    boolean forgotPassword(Users user);

    UserStatus updateUser(Users user);

    UserDetails loadUserByUsername(String username);

//    List<Users> findUserByFirstOrLastName(String username);

    Users getuserByloginId(String loginId);
}
