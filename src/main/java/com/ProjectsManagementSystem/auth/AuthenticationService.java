package com.ProjectsManagementSystem.auth;

import com.ProjectsManagementSystem.config.JwtService;
import com.ProjectsManagementSystem.exception.ApiDuplicatedLoginException;
import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.user.Token;
import com.ProjectsManagementSystem.user.TokenRepository;
import com.ProjectsManagementSystem.user.User;
import com.ProjectsManagementSystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        Token newToken = Token.builder()
                .token(jwtToken)
                .user(user)
                .isRevoked(false)
                .isExpired(false)
                .build();
        tokenRepository.save(newToken);
        user.setToken(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
         var user = userRepository.findByEmail(request.getEmail()).orElse(null);
                if(user == null){
                    throw new ApiRequestException("User not found");
                }

//                if(tokenRepository
//                        .findByUserId(user.getId()) != null){
////                    return new AuthenticationResponse("you have already logged in");
//                    throw new ApiDuplicatedLoginException("you have already logged in");
//                }

      var jwtToken = jwtService.generateToken(user);
        Token newToken = Token.builder()
                .token(jwtToken)
                .user(user)
                .isRevoked(false)
                .isExpired(false)
                .build();
        tokenRepository.save(newToken);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }

    public ResponseEntity<String> logout(Principal principal) {
        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
       Token token= tokenRepository.findByUserId(user.getId());

//        Token tokenObj = tokenRepository.findByToken(token);
//        System.out.println("wassem");
        if(token != null){
        System.out.println(token.getToken());
            token.setIsRevoked(true);
            token.setIsExpired(true);
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();
        }
        else {
            throw new ApiRequestException("user is already logged out");
        }
        return ResponseEntity.ok("logged out successfully");
    }
}
