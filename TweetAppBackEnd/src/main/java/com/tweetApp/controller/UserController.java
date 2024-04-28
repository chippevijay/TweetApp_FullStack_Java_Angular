package com.tweetApp.controller;


import com.tweetApp.JwtService.JwtUtil;
import com.tweetApp.document.Users;
import com.tweetApp.dto.UserDto;
import com.tweetApp.model.LoginResponse;
import com.tweetApp.model.UserStatus;
import com.tweetApp.model.ValidationResponse;
import com.tweetApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/tweets")
public class UserController {

    UserService userService;

    JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService,JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //Register User
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        log.info("Entering Register User...");
        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if(userDto.getPassword().equals(userDto.getConfirmPassword())){
            user.setPassword(userDto.getPassword());
        } else{
            log.info("Exiting into Register method : Password did not match..!!");
            return new ResponseEntity<>("Password did not match..!!", HttpStatus.NOT_ACCEPTABLE);
        }
        user.setLoginId(userDto.getLoginId());
        user.setEmail(userDto.getEmail());
        user.setContactNumber(userDto.getContactNumber());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setGender(userDto.getGender());
        UserStatus userStatus = userService.registerUser(user);
        if (userStatus != null){
            log.info("Exiting into Register method :" + userStatus);
            return new ResponseEntity<>(userStatus, HttpStatus.CREATED);
        }else {
            log.info("Exiting into Register method : User Creation Unsucessful");
            return new ResponseEntity<>("User Creation Unsucessful", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/login")
    ResponseEntity<LoginResponse> loginUser(@RequestBody UserDto userDto){
        log.info("Entering into loginUser method ");
        String userLoginId = userDto.getLoginId();
        String enteredPassword = userDto.getPassword();
        UserDetails userDetails = userService.findUserByLoginId(userLoginId,enteredPassword);
        log.info("Exiting from loginUser method ");
        return new ResponseEntity<>(new LoginResponse(jwtUtil.generateToken(userDetails),userDto.getLoginId()), HttpStatus.CREATED);
    }

    @PostMapping("/{loginId}/forgotpassword")
    ResponseEntity<ValidationResponse> forgotPassword(@RequestBody UserDto userDto){
        log.info("Enterning into forgotPassword method");
        Users user = new Users();
        user.setLoginId(userDto.getLoginId());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setContactNumber(userDto.getContactNumber());

        boolean forgotPasswordStatus = userService.forgotPassword(user);
        if(forgotPasswordStatus){
            log.info("Exiting from forgotPassword method");
            return new ResponseEntity<>(new ValidationResponse(true),HttpStatus.OK);
        }else {
            log.info("Exiting from forgotPassword method");
            return new ResponseEntity<>(new ValidationResponse(false),HttpStatus.OK);
        }
    }

    @PostMapping("/{loginId}/updatepassword")
    ResponseEntity<?> updatePassword(@RequestBody UserDto userDto){
        log.info("Entering into updatePassword ");
        String userLoginId = userDto.getLoginId();
        String enteredPassword = userDto.getPassword();
        Users user = new Users();
        user.setLoginId(userDto.getLoginId());
        user.setPassword(userDto.getPassword());

        UserStatus userStatus = userService.updateUser(user);
        log.info("Exiting from updatePassword ");
        return new ResponseEntity<>(userStatus, HttpStatus.OK);

    }

    @GetMapping(value = "/validate")
    public ResponseEntity<ValidationResponse> getValidity(@RequestHeader("Authorization") String token) {
        log.info("Entering getValidity controller method!!!");
        log.info("Token: {}",token);
        String localToken = token.substring(7);

        ValidationResponse response = new ValidationResponse();
        if (null!= localToken && jwtUtil.validateToken(localToken)) {
            response.setValid(true);
        }
        log.info(response.toString());
        log.info("Exiting getValidity controller method!!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Get User
    @PostMapping("/users/all")
    public ResponseEntity<List<Users>> findAllUsers(HttpServletRequest httpServletRequest){
        log.info("Entering into findAllUsers method ");
        String localToken = httpServletRequest.getHeader("Authorization");
        if(jwtUtil.validateToken(localToken)){
            log.info("Exiting from findAllUsers method ");
            return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
        }else {
            log.info("Exiting from findAllUsers method ");
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
        }
    }

//    @GetMapping("/search/{userName}")
//    public ResponseEntity<List<Users>> searchByUserName(@RequestHeader("Authorization") String token,
//                                                        @PathVariable(name="userName") String userName){
//        log.info("Entering into searchByUserName method ");
//        String localToken = token.substring(7);
//        if(jwtUtil.validateToken(localToken)){
//            log.info("Exiting from searchByUserName method ");
//            return new ResponseEntity<>(userService.findUserByFirstOrLastName(userName),HttpStatus.OK);
//        }else {
//            log.info("Exiting from searchByUserName method ");
//            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
//        }
//    }

    @GetMapping("/getUserByloginId")
    public ResponseEntity<Users> getUserByloginId(HttpServletRequest httpServletRequest) {
        log.info("Entering into getUserByloginId method ");
        String localToken =  httpServletRequest.getHeader("Authorization");
        ValidationResponse response = new ValidationResponse();
        if (null!= localToken && jwtUtil.validateToken(localToken)) {
            response.setValid(true);
        }
        if(!response.isValid())return new ResponseEntity<>(new Users(),HttpStatus.FORBIDDEN);
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(localToken));
        log.info("Exiting from getUserByloginId method ");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
