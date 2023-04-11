package com.awsswf.AWSFlow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {

    @JsonProperty("taskName")
    private String taskName;
    
    @JsonProperty("taskParameter")
    private String taskParameter;
}
