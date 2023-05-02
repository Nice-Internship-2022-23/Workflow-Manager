package com.awsswf.AWSFlow.aws.activities;

public class TimerTaskActivitiesImpl implements TimerTaskActivities {
    
    public String TimerTask(Long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Timer Task Completed successfully";
    }

    @Override
    public String performTimerTask(String result) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performTimerTask'");
    }
    
}