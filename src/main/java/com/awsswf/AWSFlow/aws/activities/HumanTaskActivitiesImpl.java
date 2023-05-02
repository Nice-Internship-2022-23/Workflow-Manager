package com.awsswf.AWSFlow.aws.activities;

public class HumanTaskActivitiesImpl implements HumanTaskActivities {
    
    @Override
    public String performHumanTask(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Human Task Completed successfully";
    }
    
}

