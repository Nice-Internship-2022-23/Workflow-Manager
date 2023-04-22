package com.awsswf.AWSFlow.aws.activities;


public class AutomatedTaskActivitiesImpl implements AutomatedTaskActivities {

    @Override
    public String performAutomatedTask() {
        System.out.println("Peforming Automated task...");
        try {
            Thread.sleep(2000);
            return "Automated Task";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage().toString();
        }
        // Perform automated task here
    }
    
}

