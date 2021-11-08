package com.uala.services.handler;

import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uala.services.processor.ServiceSNS;
import com.uala.services.processor.ServiceSNS;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContactTriggerHandler<IMAGE> implements RequestHandler<DynamodbEvent, Boolean> {

    public ContactTriggerHandler() {
            ServiceSNS.createSnsClient(SnsClient.builder().region(Region.US_EAST_1).build());
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Boolean handleRequest(DynamodbEvent ddbEvent, Context context) {
        if (ddbEvent.getRecords() == null || ddbEvent.getRecords().isEmpty()) {
            System.out.println("No records to process!");
            return false;
        }
        for (DynamodbEvent.DynamodbStreamRecord record : ddbEvent.getRecords()) {
            Optional<Map<String, AttributeValue>> optNewImage = Optional.ofNullable(record.getDynamodb().getNewImage());
            System.out.println("----- ORIGINAL IMAGE ------");
            System.out.println(record.getDynamodb().getNewImage());

            IMAGE newImage = optNewImage.map(this::getImage).orElse(null);
            System.out.println("------ PARSE ------");
            System.out.println(newImage);
            try {
                ServiceSNS.pubTopic(mapper.writeValueAsString(newImage));
                System.out.println("Topic was published");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public IMAGE getImage(Map<String, AttributeValue> item) {
        try {
            return mapper.readValue(ItemUtils.toItem(item).toJSON(), new TypeReference<HashMap<String,Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}



