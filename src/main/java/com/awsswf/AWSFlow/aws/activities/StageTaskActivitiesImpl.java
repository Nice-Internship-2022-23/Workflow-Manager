package com.awsswf.AWSFlow.aws.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.awsswf.AWSFlow.aws.NiceChildWorkerClientExternal;
import com.awsswf.AWSFlow.aws.NiceChildWorkerClientExternalFactory;
import com.awsswf.AWSFlow.aws.NiceChildWorkerClientExternalFactoryImpl;
import com.awsswf.AWSFlow.config.MySWFClient;
import com.awsswf.AWSFlow.model.Task;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.model.DescribeWorkflowExecutionRequest;
import com.amazonaws.services.simpleworkflow.model.EventType;
import com.amazonaws.services.simpleworkflow.model.GetWorkflowExecutionHistoryRequest;
import com.amazonaws.services.simpleworkflow.model.History;
import com.amazonaws.services.simpleworkflow.model.HistoryEvent;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecutionDetail;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecutionInfo;

public class StageTaskActivitiesImpl implements StageTaskActivities {

    NiceChildWorkerClientExternalFactory factory = new NiceChildWorkerClientExternalFactoryImpl(MySWFClient.getSWF(),
            MySWFClient.DOMAIN);

    Promise<Map<String, String>> result;
    String message = "";

    @Override
    public String performActivities(String stageName, ArrayList<Task> taskList) {

        // directaly invoking activities for every task.
        // for (Task task : taskList) {
        // switch (task.getTaskName()) {
        // case "Notification":
        // result = notificationTaskActivitiesClient.sendNotification(stageName,
        // stageName);
        // System.out.println(result);
        // sleep(1000);
        // break;

        // case "Timer":
        // result = timerTaskActivitiesClient.performTimerTask();
        // System.out.println(result);
        // sleep(1000);
        // break;

        // case "Automated":
        // result = automatedTaskActivitiesClient.performAutomatedTask();
        // System.out.println(result);
        // sleep(1000);
        // break;

        // case "Dependency":
        // result = dependencyTaskActivitiesClient.performDependencyTask();
        // System.out.println(result);
        // sleep(1000);
        // break;

        // case "Human":
        // result = humanTaskActivitiesClient.performHumanTask();
        // System.out.println(result);
        // sleep(1000);
        // break;

        // default:
        // System.out.println(task.getTaskName());

        // }

        // }

        // starting new workflow for every tasklist.
        NiceChildWorkerClientExternal childWorker = factory.getClient();
        childWorker.performActivities(stageName, taskList);

        // waitForWorkflowExecutionCompletion(stageName, childWorker);
    
        // Map<String, String> response = new HashMap<>();
        // response.put("MESSAGE", message);
        // response.put("EXECUTIONID", childWorker.getWorkflowExecution().getWorkflowId());
        // response.put("RUNID", childWorker.getWorkflowExecution().getRunId());
        // return response;

        
        while(!waitForWorkflowExecutionCompletion(stageName, childWorker)){
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        }
        return "Tasks in " + stageName + " are performed successfully";

    }

    public boolean waitForWorkflowExecutionCompletion(String stageName, NiceChildWorkerClientExternal childWorker) {
        WorkflowExecution workflowExecution = childWorker.getWorkflowExecution();
        WorkflowExecutionDetail detail = MySWFClient.getSWF()
                .describeWorkflowExecution(new DescribeWorkflowExecutionRequest()
                        .withDomain(MySWFClient.DOMAIN)
                        .withExecution(workflowExecution));
        String status = detail.getExecutionInfo().getExecutionStatus();
        if(status.equals("CLOSED")){
            return true;
        }
        return false;
    }

    // private void sleep(int i) {
    // try {
    // Thread.sleep(i);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
}
