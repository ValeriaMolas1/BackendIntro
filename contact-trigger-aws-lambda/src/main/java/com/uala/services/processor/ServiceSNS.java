package com.uala.services.processor;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

public class ServiceSNS {
    private static final String ARN = "arn:aws:sns:us-east-1:161142984839:molas-contacts-sns";
    private static SnsClient snsClient;

    public static void createSnsClient(SnsClient snsClienteAux)  {
        snsClient = snsClienteAux;
    }

    public static void pubTopic(String json) {

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(json)
                    .topicArn(ARN)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());
        } catch (SnsException e) {
            System.err.println("pubTopic"+e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static SnsClient getSnsClient() {
        return snsClient;
    }

    public static String getARN() {
        return ARN;
    }
}
