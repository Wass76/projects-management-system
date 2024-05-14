package com.ProjectsManagementSystem.auth;

import com.ProjectsManagementSystem.config.JwtService;
import com.ProjectsManagementSystem.mail.MailService;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation

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
