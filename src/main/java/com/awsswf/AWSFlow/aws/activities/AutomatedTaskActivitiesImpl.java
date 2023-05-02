package com.awsswf.AWSFlow.aws.activities;


public class AutomatedTaskActivitiesImpl implements AutomatedTaskActivities {

    @Override
    public String performAutomatedTask(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Automated Task Completed successfully";
    }
    
}

