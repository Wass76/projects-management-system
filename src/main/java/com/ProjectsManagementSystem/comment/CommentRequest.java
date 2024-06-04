package com.ProjectsManagementSystem.comment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentRequest {
private String comment;
private Integer bugId;
}
