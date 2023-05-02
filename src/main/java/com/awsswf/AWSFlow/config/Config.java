package com.awsswf.AWSFlow.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

@Configuration
public class Config {

    public static String DOMAIN = "Domain-7";
    public static String TASKLIST = "NiceTaskList";
    public static String REGIONS = "ap-south-1";
    private static String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
    private static String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

    protected static BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

    public static String getExecutionId(){
        String ExecutionId = UUID.randomUUID().toString();
        return ExecutionId;
    }
    
    @Bean
    public static AmazonSimpleWorkflow getSWF() {
        AmazonSimpleWorkflow swfClient = AmazonSimpleWorkflowClientBuilder.standard()
                    .withRegion(REGIONS)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

        return swfClient;
    }

    @Bean
    public static AmazonSNS getSNS(){
        AmazonSNS sns = AmazonSNSClient.builder()
						.withRegion(REGIONS)
						.withCredentials(new AWSStaticCredentialsProvider(credentials))
						.build();

        return sns;
    }

    @Bean
    public static AmazonSimpleEmailService getSES(){
        AmazonSimpleEmailService ses = AmazonSimpleEmailServiceClient.builder()
						.withRegion(REGIONS)
						.withCredentials(new AWSStaticCredentialsProvider(credentials))
						.build();

        return ses;
    }

}
