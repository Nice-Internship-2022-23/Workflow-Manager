package com.awsswf.AWSFlow.aws;


import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;

@Workflow
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 31536000)
public interface NiceWorkflowWorker {
   @Execute(name="NiceWorkflow", version = "1.0")
   public void initiateWorkflow(String workflowId, WorkflowExecution workflowExecution);
}
