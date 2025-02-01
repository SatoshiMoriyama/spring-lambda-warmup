package com.amazonaws.serverless.sample.springboot3.model;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@Data
@DynamoDbBean
public class Pet {
    private String id;
    private String breed;
    private String name;
    private Instant dateOfBirth;  // DateではなくInstantを使用

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}