package com.ProjectsManagementSystem.task;

import com.ProjectsManagementSystem.bug.Bug;
import com.ProjectsManagementSystem.project.Project;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TaskResponse {
    private int taskId;
//    private String taskName;
    private String taskDescription;
    private TaskStatus taskStatus;
    private boolean hasBugs;
    private List<Bug> bugs;
    private Integer taskProject;
    private LocalDateTime taskCreateDate;
    private LocalDateTime taskUpdateDate;
    private Integer taskCreatedBy;
    private Integer taskUpdatedBy;

}
