package com.tweetApp.kafkalistener;

import com.tweetApp.document.Tweets;
import com.tweetApp.repository.TweetsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class TweetListner {
	
/*	@Autowired
	TweetsRepository tweetsRepository;
	
	@KafkaListener(topics = "tweets" , groupId = "tweet_group",containerFactory = "kafkaTweetListenerContainerFactory")
	public void consumeTweets(Tweets tweet) {
		log.info("Entering consume tweets method in listner");
		tweetsRepository.save(tweet);
		log.info("Exiting consume tweets method in llistner");
		
	}*/

}
