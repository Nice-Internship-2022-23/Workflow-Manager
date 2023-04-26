package com.awsswf.AWSFlow.aws.activities;

public class HumanTaskActivitiesImpl implements HumanTaskActivities {
    
    @Override
    public String performHumanTask() {
        System.out.println("Peforming Human task...");
        try {
            Thread.sleep(20000);
            return "Human Task";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage().toString();
        }
        // Perform human task here
    }
    
}

