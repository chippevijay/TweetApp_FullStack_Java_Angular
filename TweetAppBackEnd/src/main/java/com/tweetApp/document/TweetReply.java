package com.tweetApp.document;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@DynamoDBDocument
public class TweetReply {

    @DynamoDBAttribute
    private String loginId;

    @DynamoDBAttribute
    private String replyMsg;

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = Tweets.LocalDateTimeConverter.class)
    private LocalDateTime creationTime;

    static public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
        @Override
        public String convert( final LocalDateTime time ) {
            return time.toString();
        }
        @Override
        public LocalDateTime unconvert( final String stringValue ) {
            return LocalDateTime.parse(stringValue);
        }
    }

}
