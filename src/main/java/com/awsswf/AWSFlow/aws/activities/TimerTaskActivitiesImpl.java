package com.awsswf.AWSFlow.aws.activities;

public class TimerTaskActivitiesImpl implements TimerTaskActivities {
    
    @Override
    public String performTimerTask(String result) {
        System.out.println("Peforming Timer task...");
        try {
            Thread.sleep(2000);
            return "Timer task";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage().toString();
        }
        // perform timer task
    }
    
}