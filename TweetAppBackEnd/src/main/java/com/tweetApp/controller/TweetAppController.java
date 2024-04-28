package com.tweetApp.controller;

import com.tweetApp.JwtService.JwtUtil;
import com.tweetApp.document.TweetReply;
import com.tweetApp.document.Tweets;
import com.tweetApp.model.ReplyRequest;
import com.tweetApp.document.Users;
import com.tweetApp.model.ValidationResponse;
import com.tweetApp.service.TweetsService;
import com.tweetApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("tweets")
public class TweetAppController {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    TweetsService tweetsService;
    @Autowired
    UserService userService;

    @PostMapping("/{loginId}/addnewtweet")
    public ResponseEntity<?> addNewTweet(@RequestHeader(name ="Authorization") String token,
                                         @RequestBody Tweets tweet){
        log.info("Enterning into addnewtweet method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse response = feignClientUserAndAuthentication.getValidity(token);
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if(null != user) {
            if(response.isValid()) {
                log.info("Returning after saving tweet");
                return new ResponseEntity<>(tweetsService.addTweet(tweet, user), HttpStatus.OK);
            }
        }
        log.info("User does not exist for the token");
        log.info("Exiting into addnewtweet method");
        return new ResponseEntity<>(new Tweets(),HttpStatus.FORBIDDEN);
    }

    @PostMapping("/{loginId}/updatetweet")
    public ResponseEntity<?> updateTweet(@RequestHeader(name ="Authorization") String token,
                                         @RequestBody Tweets tweet){
        log.info("Enterning into updateTweet method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse response = feignClientUserAndAuthentication.getValidity(token);
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if(null != user) {
            if(response.isValid()) {
                log.info("Returning after saving tweet");
                return new ResponseEntity<>(tweetsService.updateTweet(user.getLoginId(), tweet), HttpStatus.OK);
            }
        }
        log.info("User does not exist for the token");
        log.info("Exiting into updateTweet method");
        return new ResponseEntity<>(new Tweets(),HttpStatus.FORBIDDEN);
    }

    @PostMapping("/deletetweet/{tweetId}")
    public ResponseEntity<?> deleteTweet(@RequestHeader(name ="Authorization") String token,
                                         @PathVariable(name ="tweetId") String tweetId){
        log.info("Enterning into deleteTweet method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse response = feignClientUserAndAuthentication.getValidity(token);
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if(null != user) {
            if(response.isValid()) {
                log.info("Returning after saving tweet");
                return new ResponseEntity<>(tweetsService.deleteTweet(tweetId),HttpStatus.OK);
            }
        }
        log.info("User does not exist for the token");
        log.info("Exiting into deleteTweet method");
        return new ResponseEntity<>(false,HttpStatus.FORBIDDEN);
    }

    @GetMapping("/like/{tweetId}")
    public ResponseEntity<?> likeTweet(@RequestHeader(name ="Authorization") String token,
                                         @PathVariable(name ="tweetId") String tweetId){
        log.info("Enterning into likeTweet method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse response = feignClientUserAndAuthentication.getValidity(token);
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if(null != user) {
            if(response.isValid()) {
                log.info("Returning after saving tweet");
                return new ResponseEntity<>(tweetsService.likeTweet(tweetId,user.getLoginId()),HttpStatus.OK);
            }
        }
        log.info("User does not exist for the token");
        log.info("Exiting into likeTweet method");
        return new ResponseEntity<>(false,HttpStatus.FORBIDDEN);
    }

    @PostMapping("/replytweet/{tweetId}")
    public ResponseEntity<?> replyTweet(@RequestHeader(name ="Authorization") String token,
                                        @RequestBody ReplyRequest replyRequest,
                                        @PathVariable (name ="tweetId") String tweetId){
        log.info("Enterning into replyTweet method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse response = feignClientUserAndAuthentication.getValidity(token);
        Users user = userService.getuserByloginId(jwtUtil.extractUsername(token));
        if(null != user) {
            if(response.isValid()) {
                log.info("Returning after saving tweet");
                return new ResponseEntity<>(tweetsService.replyTweet(replyRequest, user.getLoginId(), tweetId),HttpStatus.OK);
            }
        }
        log.info("User does not exist for the token");
        log.info("Exiting into replyTweet method");
        return new ResponseEntity<>(new TweetReply(),HttpStatus.FORBIDDEN);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tweets>> getAllTweets(@RequestHeader(name ="Authorization") String token){
        log.info("Enterning into getAllTweets method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse response = feignClientUserAndAuthentication.getValidity(token);
        if(response.isValid()) {
            log.info("Returning after saving tweet");
            return new ResponseEntity<>(tweetsService.getAllTweets(),HttpStatus.OK);
        }
        log.info("User does not exist for the token");
        log.info("Exiting into getAllTweets method");
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
    }

    @GetMapping( value ="/getTweetsByloginId/{loginId}")
    public ResponseEntity<List<Tweets>> getTweetByloginId(@RequestHeader(name ="Authorization") String token,
                                                         @PathVariable(name ="loginId") String loginId) {
        log.info("Enterning into getTweetByloginId method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse respose = feignClientUserAndAuthentication.getValidity(token);

        if(response.isValid()) {
            return new ResponseEntity<>(tweetsService.getTweetByloginId(loginId),HttpStatus.OK);
        }
        log.info("Exiting into getTweetByloginId method");
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);

    }

  /*  @GetMapping( value ="/getTweetsByUserName/{userName}")
    public ResponseEntity<List<Tweets>> getTweetByUserName(@RequestHeader(name ="Authorization") String token,
                                                          @PathVariable(name ="userName") String userName) {
        log.info("Enterning into getTweetByUserName method");
        ValidationResponse response = new ValidationResponse();
        if (null!= token && jwtUtil.validateToken(token)) {
            response.setValid(true);
        }
//        ValidationResponse respose = feignClientUserAndAuthentication.getValidity(token);

        if(response.isValid()) {
            return new ResponseEntity<>(tweetsService.getTweetByUserName(userName),HttpStatus.OK);
        }
        log.info("Exiting into getTweetByUserName method");
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);

    }*/


}
