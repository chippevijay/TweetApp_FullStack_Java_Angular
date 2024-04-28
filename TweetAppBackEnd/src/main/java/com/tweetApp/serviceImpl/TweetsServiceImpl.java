package com.tweetApp.serviceImpl;

import com.tweetApp.document.TweetLikes;
import com.tweetApp.document.TweetReply;
import com.tweetApp.document.Tweets;
import com.tweetApp.exception.TweetAddFailedException;
import com.tweetApp.exception.TweetNotFoundException;
import com.tweetApp.model.ReplyRequest;
import com.tweetApp.document.Users;
import com.tweetApp.repository.TweetsRepository;
import com.tweetApp.repository.UserRepository;
import com.tweetApp.service.TweetsService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TweetsServiceImpl implements TweetsService {

//    @Autowired
//    KafkaTemplate<String, Tweets> kafkaTemplate;
    @Autowired
    TweetsRepository tweetsRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public Tweets addTweet(Tweets tweet, Users users) {
        log.info("Entering into addTweet method");
//        tweet.setTweetId(sequenceGeneratorService.generateSequence(Tweets.SEQUENCE_NAME));
        tweet.setCreatedByName(users.getFirstName()+" "+users.getLastName());
        tweet.setCreatedById(users.getLoginId());
        tweet.setUpdateDateTime(LocalDateTime.now());
        tweet.setTweetLikesCount(0);
        tweetsRepository.save(tweet);
//        ListenableFuture<SendResult<String, Tweets>> future = kafkaTemplate.send("tweet", tweet);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Tweets>>() {
//            @Override
//            public void onSuccess(SendResult<String, Tweets> result) {
//                log.info("Successfully published to tweet topic");
//
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                throw new TweetAddFailedException();
//            }
//        });
        log.info("Exiting into addTweet method");
        return tweet;
    }

    @Override
    public Tweets updateTweet(String loginId, Tweets tweet) {
        log.info("Entering into updateTweet method");
        Tweets existingTweet = tweetsRepository.findById(tweet.getTweetId())
                .orElseThrow(() -> new TweetNotFoundException());
        if (existingTweet.getCreatedById().equals(loginId)){
            existingTweet.setUpdateDateTime(LocalDateTime.now());
            existingTweet.setMessage(tweet.getMessage());
            if(tweet.getTag()!=null){
                existingTweet.setTag(tweet.getTag());
            }
//            tweetsRepository.deleteByTweetId(tweet.getTweetId());
            tweetsRepository.edit(existingTweet);
        }
        log.info("Exiting into updateTweet method");
        return tweet;
    }

    @Override
    public boolean deleteTweet(String tweetId) {
        tweetsRepository.deleteByTweetId(tweetId);
        return true;
    }

    @Override
    public Tweets likeTweet(String tweetId, String loginId) {
        log.info("Entering into likeTweet method");
        Tweets existingTweet = tweetsRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException());
        List<TweetLikes> list=existingTweet.getTweetLikes();

        List<TweetLikes> userliked =  list.stream().filter(a->a.getLoginId().equals(loginId)).collect(Collectors.toList());
        if(userliked.isEmpty()) {
            TweetLikes likes=new TweetLikes();
            likes.setLikedTime(LocalDateTime.now());
            likes.setLoginId(loginId);

            list.add(likes);
            existingTweet.setTweetLikesCount(existingTweet.getTweetLikesCount()+1);
        }else {
            list.remove(userliked.get(0));
            existingTweet.setTweetLikesCount(existingTweet.getTweetLikesCount()-1);
        }
        log.info("Exiting into likeTweet method");
//        tweetsRepository.deleteByTweetId(tweetId);
        return tweetsRepository.save(existingTweet);
    }


    @Override
    public TweetReply replyTweet(ReplyRequest replyRequest, String loginId, String tweetId) {
        log.info("Entering into replyTweet method");
        Tweets existingTweet = tweetsRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException());
        List<TweetReply> replyList = existingTweet.getTweetReply();

        TweetReply reply=new TweetReply();
        reply.setCreationTime(LocalDateTime.now());
        reply.setReplyMsg(replyRequest.getReplyMsg());
        reply.setLoginId(loginId);

        replyList.add(reply);

        existingTweet.setTweetReply(replyList);

        tweetsRepository.deleteByTweetId(tweetId);
        tweetsRepository.save(existingTweet);


//        ListenableFuture<SendResult<String, Tweets>> future = kafkaTemplate.send("tweet", existingTweet);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Tweets>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, Tweets> result) {
//                log.info("Successfully published to tweet topic");
//
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                log.info(ex.getMessage());
//                throw new TweetAddFailedException();
//            }
//
//        });
        log.info("Exiting into replyTweet method");
        return reply;
    }

    @Override
    public List<Tweets> getAllTweets() {
        return tweetsRepository.findAll();
    }

    @Override
    public List<Tweets> getTweetByloginId(String loginId) {
        Users user = userRepository.findById(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));

        List<Tweets> tweets = tweetsRepository.findAll();
        List<Tweets> tweetsByUser = new ArrayList<>();
        tweets.forEach(tweet -> {
            if(tweet.getCreatedById().equals(loginId)){
                tweetsByUser.add(tweet);
            }
        });
        tweetsByUser.sort(Comparator.comparing(Tweets::getUpdateDateTime).reversed());
        return tweetsByUser;
    }

//    @Override
//    public List<Tweets> getTweetByUserName(String userName) {
//        return tweetsRepository.findByCreatedByName(userName);
//    }

}
