package com.uala.services.repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.uala.services.enums.EnumStatus;
import com.uala.services.model.Contact;

public class Repository {

    private final Regions REGION = Regions.US_EAST_1;
    private DynamoDBMapper dynamoDBMapper;

    public Repository() {
        this.dynamoDBMapper = new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION.getName())
                .build());
    }

    public <T> T load(Class<T> clazz, String id, String firstName, Object hashKey) {
        return this.dynamoDBMapper.load(clazz , hashKey);
    }

    public <T> T load(Class<T> clazz, Object hashKey, Object rangeKey) { return this.dynamoDBMapper.load(clazz , hashKey, rangeKey); }

    public void updateContact(Contact contact) {
        Contact contactTemp = load(Contact.class, contact.getId() ,contact.getFirstName(),contact.getLastName());
        contact.setStatus(EnumStatus.PROCESSED);
        this.dynamoDBMapper.save(contact, (DynamoDBSaveExpression)null);
    }
}