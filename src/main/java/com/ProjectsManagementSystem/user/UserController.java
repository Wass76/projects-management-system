package com.ProjectsManagementSystem.user;

import com.ProjectsManagementSystem.mail.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Account Management")
public class UserController {

    private final UserService userService;

    @Operation(
            description = "This endpoint build for make user able to change his password by enter old and new password",
            summary =  "Change password of user",
            responses ={
                    @ApiResponse(
                        description = "Change password done successfully",
                        responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Wrong old password / Passwords are not the same",
                            responseCode = "403"
                    )
            }
    )
    @PutMapping("/change-password")
//    @PreAuthorize()
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    )
    {
        userService.changePassword(request , connectedUser);
        return ResponseEntity.accepted().body("Change password done successfully");
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgotPassword(String email){
         userService.initiatePasswordReset(email);
         return ResponseEntity.accepted().body("Restoration code sent successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request,String token){

        userService.resetPassword(token, request);

        return ResponseEntity.accepted().body("Reset password done successfully");
    }
}
