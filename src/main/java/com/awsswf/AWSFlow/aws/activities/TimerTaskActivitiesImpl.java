package com.awsswf.AWSFlow.aws.activities;

public class TimerTaskActivitiesImpl implements TimerTaskActivities {

    @Override
    public String performTimerTask(Long time) {
        try {
            Thread.sleep(time*1000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return "Timer is not parsable";
        }
        return "Timer Task Completed successfully";
    }
    
}