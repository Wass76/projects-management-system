package com.ProjectsManagementSystem.auth;

import com.ProjectsManagementSystem.mail.MailService;
import com.ProjectsManagementSystem.mail.MailStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationContoller {

    private final AuthenticationService authenticationService;
    private final MailService mailService;


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

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
       return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
