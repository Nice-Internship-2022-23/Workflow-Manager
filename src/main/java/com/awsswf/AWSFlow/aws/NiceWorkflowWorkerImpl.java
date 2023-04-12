package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivities;
import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivities;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivities;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivities;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivities;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesImpl;
import com.awsswf.AWSFlow.model.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
public class NiceWorkflowWorkerImpl implements NiceWorkflowWorker {

  @Override
  public void initiateWorkflow(String workflowID) {
      
      RestTemplate restTemplate = new RestTemplate();

      String url = "http://localhost:8080/api/getTasks/";

      String apiUrl = url + workflowID;

      ParameterizedTypeReference<ArrayList<Task>> responseType = new ParameterizedTypeReference<ArrayList<Task>>() {
      };

      ResponseEntity<ArrayList<Task>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null,
              responseType);
      ArrayList<Task> response = responseEntity.getBody();

      ObjectMapper mapper = new ObjectMapper();

      try {
          ArrayList<Task> tasks = mapper.readValue(mapper.writeValueAsString(response),
                  new TypeReference<ArrayList<Task>>() {
                  });

          for (Task task : tasks) {
              switch (task.getTaskName()) {
                  case "Notification":
                      NotificationTaskActivities notificationTaskActivities = new NotificationTaskActivitiesImpl();
                      notificationTaskActivities.sendNotification("", "");
                      System.out.println("Notification Task performed successfully.");
                      break;

                  case "Timer":
                      TimerTaskActivities timerTaskActivities = new TimerTaskActivitiesImpl();
                      timerTaskActivities.performTimerTask();
                      System.out.println("Timer Task performed successfully.");
                      break;

                  case "Automated":
                      AutomatedTaskActivities automatedTaskActivities = new AutomatedTaskActivitiesImpl();
                      automatedTaskActivities.performAutomatedTask();
                      System.out.println("Automated Task performed successfully.");
                      break;

                  case "Dependency":
                      DependencyTaskActivities dependencyTaskActivities = new DependencyTaskActivitiesImpl();
                      dependencyTaskActivities.performDependencyTask();
                      System.out.println("Dependency Task performed successfully.");
                      break;

                  case "Human":
                      HumanTaskActivities humanTaskActivities = new HumanTaskActivitiesImpl();
                      humanTaskActivities.performHumanTask();
                      System.out.println("Human Task performed successfully.");
                      break;

                  default:
                      
              }
          }

          System.out.println("All tasks are performed successfully.");

      } catch (JsonProcessingException e) {
          e.printStackTrace();
      }
  }
 }
