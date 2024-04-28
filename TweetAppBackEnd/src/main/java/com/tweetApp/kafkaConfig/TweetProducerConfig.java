package com.tweetApp.kafkaConfig;

//import com.tweetApp.document.Tweets;
import lombok.extern.slf4j.Slf4j;
/*import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;*/

import java.util.HashMap;
import java.util.Map;


//@Configuration
@Slf4j
public class TweetProducerConfig {
	
	/*@Value("${kafka.server}")
	private String serverUrl;
	

	@Bean
	public ProducerFactory<String, Tweets> producerTweetFactory() {
		log.info("Entering into producerTweetFactory method");
	    return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		log.info("Entering into producerConfigs method");
	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverUrl);
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		log.info("Exiting into producerConfigs method");
	    return props;
	}

	@Bean
	public KafkaTemplate<String, Tweets> kafkaTweetTemplate() {
	    return new KafkaTemplate<>(producerTweetFactory());
	}
*/
}
