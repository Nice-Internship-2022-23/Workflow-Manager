package com.awsswf.AWSFlow.aws.activities;

public class DependencyTaskActivitiesImpl implements DependencyTaskActivities{

    @Override
    public String performDependencyTask(String result) {
        System.out.println("Peforming Dependency task...");
        try {
            Thread.sleep(2000);
            return "Dependency Task";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage().toString();
        }
        // perform dependency task
    }
    
}
