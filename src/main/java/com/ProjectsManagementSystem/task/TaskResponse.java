package com.ProjectsManagementSystem.task;

import com.ProjectsManagementSystem.project.Project;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private int taskId;
    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;
    private Project taskProject;
    private LocalDateTime taskCreateDate;
    private LocalDateTime taskUpdateDate;
    private Integer taskCreatedBy;
    private Integer taskUpdatedBy;

}
