package com.awsswf.AWSFlow.aws;

public class NiceActivityWorkerImpl implements NiceActivityWorker{

    @Override
    public String performNotificationTaskActivity(String message, String receipant, String result) {
        return "Notification Task";
    }

    @Override
    public String performHumanTaskActivity(String result) {
        return "Human Task";
    }

    @Override
    public String performAutomatedTaskActivity(String result) {
        return "Automated Task";
    }

    @Override
    public String performDependencyTaskActivity(String result) {
        return "Dependency Task";
    }

    @Override
    public String performTimerTaskActivity(String result, Long time) {
        return "Timer Task";
    }

    @Override
    public void performStageTaskActivity(String stage, String result) {
        System.out.println("Task in stage " + stage + " completed.");
    }
    
}
