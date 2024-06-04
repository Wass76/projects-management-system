package com.ProjectsManagementSystem.bug;

import lombok.Data;

@Data
public class BugRequest {
    private String title;
    private String description;
    private Integer priority;
    private Integer taskId;
}
