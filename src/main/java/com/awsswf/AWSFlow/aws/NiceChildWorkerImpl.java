package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;

import com.awsswf.AWSFlow.aws.activities.StageTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.StageTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.model.Task;

public class NiceChildWorkerImpl implements NiceChildWorker {
    StageTaskActivitiesClient activityClient = new StageTaskActivitiesClientImpl();

    @Override
    public void performActivities(String stageName, ArrayList<Task> taskList) {
        activityClient.performActivities(stageName, taskList);
    }

}
