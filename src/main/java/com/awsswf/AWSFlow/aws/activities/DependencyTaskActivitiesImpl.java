package com.awsswf.AWSFlow.aws.activities;

public class DependencyTaskActivitiesImpl implements DependencyTaskActivities{

    @Override
    public String performDependencyTask(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Dependency Task Completed successfully";
    }
    
}
