package com.ProjectsManagementSystem.auth;

import com.ProjectsManagementSystem.config.JwtService;
import com.ProjectsManagementSystem.exception.ApiDuplicatedLoginException;
import com.ProjectsManagementSystem.mail.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final MailService mailService;
    private final JwtService jwtService;


    @Operation(
            description = "This endpoint build to register a new user account",
            summary = "Register an new account",
            responses = {
                    @ApiResponse(
                            description = "register done successfully",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "You can't register because email already in use in this system, try with another email",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ApiDuplicatedLoginException.class)
                            )
                    )
            }
    )

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
//        MailStructure mailStructure = MailStructure.builder()
//                .subject("Welcome to our system")
//                .message("hi, this is customer service section in our system" +
//                        " and we want to say welcome to you in our project management system")
//                .build();
//        String mailAddress = request.getEmail();
//        mailService.sendEmail(mailAddress, mailStructure);
       return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(
            description = "This endpoint build to login to user account",
            summary = "Login to my account",
            responses = {
                    @ApiResponse(
                            description = "Logging in done successfully",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            description = "You can't login because you already login, make logout operation firstly and try again",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = ApiDuplicatedLoginException.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
       return authenticationService.authenticate(request);
    }

    @PutMapping("/logout")
    public ResponseEntity<String> logout(
            Principal principal
    ){
//        System.out.println("Logging out");
        return authenticationService.logout(principal
        );
    }
@PostMapping("/refresh_token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    authenticationService.refreshToken(request,response);
}

}
