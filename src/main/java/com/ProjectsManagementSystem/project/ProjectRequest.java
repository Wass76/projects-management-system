package com.ProjectsManagementSystem.project;

import lombok.Data;

@Data
public class ProjectRequest {
    private String projectName;
    private String projectDescription;
    private ProjectStatus projectStatus;
}
