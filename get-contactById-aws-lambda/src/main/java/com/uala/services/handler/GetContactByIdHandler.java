package com.uala.services.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.uala.services.model.Contact;
import com.uala.services.model.Request;

public class GetContactByIdHandler implements RequestHandler<Request, Contact> {
    private final Regions REGION = Regions.US_EAST_1;
    private DynamoDBMapper dynamoMapper;

    public GetContactByIdHandler() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION.getName()).build();
        this.dynamoMapper = new DynamoDBMapper(client);
    }

    public GetContactByIdHandler(AmazonDynamoDB client) {
        this.dynamoMapper = new DynamoDBMapper(client);
    }

    @Override
    public Contact handleRequest(Request request, Context context) {
        Contact contact = this.dynamoMapper.load(Contact.class, request.getId());
        return contact;
    }
}