package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;
import com.awsswf.AWSFlow.model.Task;

@Workflow
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 3000000)
public interface NiceChildWorker {

    @Execute(version = "1.0")
    void performActivities(String stageName, ArrayList<Task> taskList);

}
