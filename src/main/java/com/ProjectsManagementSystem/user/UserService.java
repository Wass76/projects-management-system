package com.ProjectsManagementSystem.user;

import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.mail.MailService;
import com.ProjectsManagementSystem.mail.MailStructure;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final MailService mailService;

    public void changePassword(
            ChangePasswordRequest request, Principal connectedUser){

        var user  = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        //check if currentPassword is correct
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new ApiRequestException("Wrong password");
//          throw new IllegalStateException("Wrong password");
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new ApiRequestException("Password are not the same");
//            throw new IllegalStateException("Password are not the same");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void initiatePasswordReset(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ApiRequestException("User not found with email " + email));

        String token = generateResetToken();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime expiryTime = localDateTime.plusMinutes(30);
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(expiryTime)
                .build();

        MailStructure mailStructure = MailStructure.builder()
                        .subject("Reset password code")
                                .message("Your restoration code for reset your account's password is:" + token
                                +"\n" + "you should now that this restoration code is valid just for 30 minutes")
                .build();
        mailService.sendEmail(email,mailStructure);
    }

    public String generateResetToken(){
        int tokenLength = 12;
        String token = RandomStringUtils.random(tokenLength, true, true);
        while(passwordResetTokenRepository.existsByToken(token)){
            token = RandomStringUtils.random(tokenLength, true, true);
        }
        return token;
    }

    public void resetPassword(String token,ResetPasswordRequest request){
        PasswordResetToken pwResetToken = passwordResetTokenRepository.findByToken(token);
        if(pwResetToken == null || pwResetToken.getExpiryDate().isBefore(LocalDateTime.now())){ //isAfter
            throw new ApiRequestException("Token is not valid or expired");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new ApiRequestException("Password are not the same");
        }

        User user = pwResetToken.getUser();
        if(user == null){
            throw new ApiRequestException("User not found");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(pwResetToken);
    }

//    public void sendRestorationCode()

}
