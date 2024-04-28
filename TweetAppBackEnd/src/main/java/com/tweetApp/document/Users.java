package com.tweetApp.document;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@DynamoDBTable(tableName = "users")
public class Users {


    @DynamoDBHashKey(attributeName = "loginId")
    private String loginId;

    @DynamoDBAttribute
    private String firstName;

    @DynamoDBAttribute
    private String lastName;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDate dateOfBirth;

    @DynamoDBAttribute
    private String contactNumber;

    @DynamoDBAttribute
    private String gender;

    static public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDate> {
        @Override
        public String convert( final LocalDate time ) {
            return time.toString();
        }
        @Override
        public LocalDate unconvert( final String stringValue ) {
            return LocalDate.parse(stringValue);
        }
    }

}
