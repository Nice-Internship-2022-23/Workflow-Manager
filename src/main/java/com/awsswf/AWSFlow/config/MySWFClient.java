package com.awsswf.AWSFlow.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;

@Configuration
public class MySWFClient {

    public static String DOMAIN = "Domain-8";
    public static String TASKLIST = "NiceTaskList";

    public static String getExecutionId(){
        String ExecutionId = UUID.randomUUID().toString();
        return ExecutionId;
    }
    
    @Bean
    public static AmazonSimpleWorkflow getSWF() {

        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        
        AmazonSimpleWorkflow swfClient = AmazonSimpleWorkflowClientBuilder.standard()
                    .withRegion(Regions.AP_SOUTH_1)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

        return swfClient;
    }

}
