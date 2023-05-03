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
public class Input {

   @JsonProperty("fieldName")
   private String fieldName;
   
   @JsonProperty("fieldType")
   private String fieldType;

   @JsonProperty("fieldValue")
   private String fieldValue;

}
