package com.tweetApp.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tweetApp.document.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private DynamoDBMapper mapper;

    public Users save(Users user){
        mapper.save(user);
        return user;
    }

    public Optional<Users> findById(String loginId){
        Users user = mapper.load(Users.class, loginId);
        return Optional.ofNullable(user);
    }

    public Optional<Users> findUserByLoginId(String loginId){
        Users user = mapper.load(Users.class, loginId);
        return Optional.ofNullable(user);
    }

    public List<Users> findAll(){
        return mapper.scan(Users.class,new DynamoDBScanExpression());
    }

    public List<Users> findByUsernameContaining(String user){
        Map<String, AttributeValue> expAttrValues = new HashMap<>();
        expAttrValues.put(":user", new AttributeValue().withS(user));

        DynamoDBScanExpression scanExp = new DynamoDBScanExpression()
                .withFilterExpression("begins_with(username,:user)")
                .withExpressionAttributeValues(expAttrValues);

        return mapper.scan(Users.class,scanExp);
    }
}
