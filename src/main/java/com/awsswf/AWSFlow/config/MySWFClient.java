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

    private static String DOMAIN = "MyDomain";
    private static String TASKLIST = "NiceWorkerList";

    public static String getDomain(){
        return DOMAIN;
    }

    public static String getTaskList(){
        return TASKLIST;
    }

    public static String getExecutionId(){
        String ExecutionId = UUID.randomUUID().toString();
        return ExecutionId;
    }
    
    @Bean
    public static AmazonSimpleWorkflow getSWF() {

        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

        // System.out.println("AWS_ACCESS_KEY_ID : " + accessKey + "\n" + "AWS_SECRET_ACCESS_KEY : " + secretKey);
        // // String accessKey = "AKIAXIICH77FIGWEZTOI";
        // // String secretKey = "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";

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        
        AmazonSimpleWorkflow swfClient = AmazonSimpleWorkflowClientBuilder.standard()
                    .withRegion(Regions.AP_SOUTH_1)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

        return swfClient;
    }

}
