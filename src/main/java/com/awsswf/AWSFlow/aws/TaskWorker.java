package com.awsswf.AWSFlow.aws;

import org.springframework.context.annotation.Bean;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.awsswf.AWSFlow.config.MySWFClient;


public class TaskWorker<T> {

    T implementationClass;
    private AmazonSimpleWorkflow swf;
    private String domain, taskList;

    public TaskWorker(T obj) {
        this.implementationClass = obj;
        this.swf = MySWFClient.getSWF();
        this.domain = MySWFClient.getDomain();
        this.taskList = MySWFClient.getTaskList();
    }

    @Bean
    public ActivityWorker startWorker() {
        ActivityWorker worker = new ActivityWorker(swf, domain, taskList);
        try {
            worker.addActivitiesImplementation(implementationClass);
        } catch (InstantiationException | IllegalAccessException | SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        worker.start();
        return worker;
    }

}
