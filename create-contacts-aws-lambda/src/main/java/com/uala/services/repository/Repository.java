package com.uala.services.repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.uala.services.model.Contact;

import java.util.UUID;

public class Repository {
    private final Regions REGION = Regions.US_EAST_1;
    private DynamoDBMapper dynamoDBMapper;

    public Repository() {
        this.dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION.getName())
                .build());
    }


    public void createContact(Contact contact) {
        String id = UUID.randomUUID().toString();
        contact.setId(id);
        this.dynamoDBMapper.save(contact, (DynamoDBSaveExpression)null);
    }
}
