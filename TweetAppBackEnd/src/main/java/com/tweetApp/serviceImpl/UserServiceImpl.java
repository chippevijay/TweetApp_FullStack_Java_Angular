package com.tweetApp.serviceImpl;

import com.tweetApp.document.Users;
import com.tweetApp.exception.InvalidPasswordException;
import com.tweetApp.model.UserStatus;
import com.tweetApp.repository.UserRepository;
import com.tweetApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserStatus registerUser(Users user) {
        userRepository.save(user);
        UserStatus userStatus = new UserStatus(user.getLoginId(),"User Successfully Created");
        log.info("User Created Successfully");
        return userStatus;
    }

    public UserDetails findUserByLoginId(String loginId,String enteredPassword) {
        Users user = userRepository.findById(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        String originalPassword = user.getPassword();
        if(originalPassword.equals(enteredPassword)){
            return new User(user.getLoginId(),user.getPassword(),new ArrayList<>());
        }else{
            throw new InvalidPasswordException();
        }
    }

    @Override
    public boolean forgotPassword(Users userEnteredDetails) {
        Users userOriginalDetails = userRepository.findById(userEnteredDetails.getLoginId())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        return userEnteredDetails.getDateOfBirth().isEqual(userOriginalDetails.getDateOfBirth())
                && userEnteredDetails.getContactNumber().equals(userOriginalDetails.getContactNumber());
    }

    @Override
    public UserStatus updateUser(Users user) {
        Users userDetails = userRepository.findById(user.getLoginId())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        String enteredPassword = user.getPassword();
        String originalPassword = userDetails.getPassword();
        if (!originalPassword.equals(enteredPassword)){
            userDetails.setPassword(enteredPassword);
        }
        userRepository.save(userDetails);
        UserStatus userStatus = new UserStatus(user.getLoginId(),"Password Changed Successfully");
        log.info("User Created Successfully");
        return userStatus;
    }

    @Override
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        Users user = userRepository.findById(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        return new User(user.getLoginId(),user.getPassword(),new ArrayList<>());
    }

//    @Override
//    public List<Users> findUserByFirstOrLastName(String userName) {
//        return userRepository.findUserByFirstOrLastName(userName);
//    }

    @Override
    public Users getuserByloginId(String loginId) {
        return userRepository.findById(loginId).orElse(null);
    }
}
