package com.ProjectsManagementSystem.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    private String lastModifiedBy;
    private String CreatedBy;
}
