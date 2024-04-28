package com.tweetApp.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.tweetApp.document.Tweets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TweetsRepository {

    @Autowired
    private DynamoDBMapper mapper;


    public Tweets save(Tweets tweet) {
        mapper.save(tweet);
        return tweet;
    }

    public Optional<Tweets> findById(String tweetId){
        Tweets tweet = mapper.load(Tweets.class, tweetId);
        return Optional.ofNullable(tweet);
    }

    public Tweets edit(Tweets tweet){
        mapper.save(tweet,saveExpression(tweet));
        return tweet;
    }

    public DynamoDBSaveExpression saveExpression(Tweets tweet){
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(tweet.getTweetId())));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }

    public void deleteByTweetId(String tweetId){
        mapper.delete(mapper.load(Tweets.class,tweetId));
    }

    public List<Tweets> findAll(){
        return mapper.scan(Tweets.class,new DynamoDBScanExpression());
    }

}
