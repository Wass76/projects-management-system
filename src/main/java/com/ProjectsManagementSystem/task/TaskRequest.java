package com.ProjectsManagementSystem.task;

import lombok.Data;

@Data
public class TaskRequest {

    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private Integer project_id;
//    private Integer user_id;

}
