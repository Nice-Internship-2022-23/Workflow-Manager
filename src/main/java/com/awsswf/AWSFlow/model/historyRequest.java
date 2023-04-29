package com.awsswf.AWSFlow.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class historyRequest {
    private String workflowExecutionId, workflowRunId;
}
