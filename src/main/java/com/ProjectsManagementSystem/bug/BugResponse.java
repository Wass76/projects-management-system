package com.ProjectsManagementSystem.bug;

import com.ProjectsManagementSystem.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BugResponse {
    private int id;
    private String title;
    private String description;
    private Integer priority;
    private Integer taskId;
    private Boolean hasComments;
    private List<Comment> comments;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Integer createdBy;
    private Integer modifiedBy;
}
