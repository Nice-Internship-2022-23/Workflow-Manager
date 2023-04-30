package com.awsswf.AWSFlow.aws;

public class NiceActivityWorkerImpl implements NiceActivityWorker{

    @Override
    public String performNotificationTaskActivity(String message, String receipant, String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Notification Task Completed successfully";
    }

    @Override
    public String performHumanTaskActivity(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Human Task Completed successfully";
    }

    @Override
    public String performAutomatedTaskActivity(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Automated Task Completed successfully";
    }

    @Override
    public String performDependencyTaskActivity(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Dependency Task Completed successfully";
    }

    @Override
    public String performTimerTaskActivity(String result, Long time) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Timer Task Completed successfully";
    }

    @Override
    public void performStageTaskActivity(String stage, String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println("Task in stage " + stage + " completed.");
    }
    
}
