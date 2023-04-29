package com.awsswf.AWSFlow.aws;


import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;

@Workflow
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 3600)
public interface NiceWorkflowWorker {
    
    @Execute(name="Nice Workflow", version = "2.0")
   public void initiateWorkflow(String workflowId);

}
