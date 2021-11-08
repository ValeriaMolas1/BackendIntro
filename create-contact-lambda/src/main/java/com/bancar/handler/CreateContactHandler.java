package com.bancar.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.bancar.model.Contact;
import com.bancar.model.Response;

import com.amazonaws.services.lambda.runtime.Context;

import java.util.Optional;
import java.util.UUID;

public class CreateContactHandler implements RequestHandler<Contact, Response> {
    private static final Regions REGION = Regions.US_EAST_1;
    private DynamoDBMapper dynamoMapper;


    public CreateContactHandler() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION.getName()).build();
        this.initMapper(client);
    }

    private void initMapper(AmazonDynamoDB client) {
        this.dynamoMapper = new DynamoDBMapper(client);
    }

    public CreateContactHandler(AmazonDynamoDB client) {
        this.initMapper(client);
    }

    @Override
    public Response handleRequest(Contact contact, Context context) {


        contact.setId(UUID.randomUUID().toString());
        try {
            Optional.ofNullable(dynamoMapper.load(Contact.class, contact.getFirstName(), contact.getFirstName()))
                    .orElseThrow(() -> new RuntimeException("[CONTACT NOT FOUND]"));
        }catch (Exception e){
            this.dynamoMapper.save(contact);
            Response response = new Response();
            response.setMessage("Saved Successfully!");

            return response;

        }
        Response response = new Response();
        response.setMessage("409 Conflict ");

        return response;
    }
}
