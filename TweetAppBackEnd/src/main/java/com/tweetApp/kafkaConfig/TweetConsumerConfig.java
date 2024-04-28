package com.tweetApp.kafkaConfig;

import com.tweetApp.document.Tweets;
import lombok.extern.slf4j.Slf4j;
/*import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;*/

import java.util.HashMap;
import java.util.Map;


//@Configuration
@Slf4j
public class TweetConsumerConfig {
	/*@Value("${kafka.server}")
	private String serverUrl;
	
	@Bean
	public ConsumerFactory<String, Tweets> consumerTweetFactory(){
		log.info("Entering into consumerTweetFactory method");
		Map<String,Object> config=buildMap();
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "tweet_group");
		log.info("Exiting into consumerTweetFactory method");
		return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),new JsonDeserializer<>(Tweets.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Tweets> kafkaTweetListenerContainerFactory(){
		log.info("Entering into kafkaTweetListenerContainerFactory method");
		ConcurrentKafkaListenerContainerFactory<String, Tweets> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerTweetFactory());
		log.info("Exiting into kafkaTweetListenerContainerFactory method");
		return factory;
	}
	
	
	
	private Map<String,Object> buildMap(){
		log.info("Entering into buildMap method");
		Map<String,Object> config=new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverUrl);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class );
		log.info("Exiting into buildMap method");
		return config;
	}*/

}
