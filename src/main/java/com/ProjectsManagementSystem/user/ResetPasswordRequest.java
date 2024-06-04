package com.ProjectsManagementSystem.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResetPasswordRequest {

    private String newPassword;
    private String confirmPassword;

}
