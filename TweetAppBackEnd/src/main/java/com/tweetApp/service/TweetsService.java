package com.tweetApp.service;

import com.tweetApp.document.TweetReply;
import com.tweetApp.document.Tweets;
import com.tweetApp.model.ReplyRequest;
import com.tweetApp.document.Users;

import java.util.List;
import java.util.UUID;

public interface TweetsService {

    public Tweets addTweet(Tweets tweet, Users users);
    public Tweets updateTweet(String loginId, Tweets tweet);
    public boolean deleteTweet(String tweetId);
    public Tweets likeTweet(String tweetId, String loginId);
    public TweetReply replyTweet(ReplyRequest reply, String loginId, String tweetId);

    public List<Tweets> getAllTweets();

    public List<Tweets> getTweetByloginId(String loginId);

//    public List<Tweets> getTweetByUserName(String userName);
}
