package com.ProjectsManagementSystem.config;

import com.ProjectsManagementSystem.user.Token;
import com.ProjectsManagementSystem.user.TokenRepository;
import com.ProjectsManagementSystem.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;


    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/v2/api-docs",
            "api/v1/users/reset-password",
            "api/v1/users/forget-password",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };
    @Autowired
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository;

//    private final LogoutHandler logoutHandler;

    @Bean
    public LogoutHandler logoutHandler() {
        // You can use a default LogoutHandler or a custom implementation here
        return new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                // Implement your logout logic here (e.g., invalidate session, clear cookies)
//                HttpSession session = request.getSession(false);
//                if(session != null){
//                    session.invalidate();
//                }
//                if(authentication instanceof UsernamePasswordAuthenticationToken token) {
//                    var user = (User)token.getPrincipal();
//                    Token myToken = tokenRepository.findByUserId(user.getId());
////                    System.out.println("myToken: " + myToken);
//                    if(myToken != null) {
//                        myToken.setIsRevoked(true);
//                        tokenRepository.save(myToken);
//                    }
//                    response.setStatus(HttpServletResponse.SC_OK);
//                    try {
//                        response.getWriter().write("{\"message\": \"Logout successful\"}"); //
////                        response.getWriter().flush();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()

                                .requestMatchers("api/v1/mail/**").authenticated()
                                .requestMatchers("api/v1/users/change-password/**").authenticated()
                                .requestMatchers("api/v1/projects/**").authenticated()
                                .requestMatchers("api/v1/tasks/**").authenticated()
                                .requestMatchers("api/v1/bugs/**").authenticated()
                                .requestMatchers("api/v1/comments/**").authenticated()
                                .requestMatchers("api/v1/auth/logout").authenticated()

                      //  req.requestMatchers("/api/v1/**").authenticated()
                      //  req.anyRequest()
                           //     .permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")
//                                .addLogoutHandler(logoutHandler())
//                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                );
        return http.build();
    }
}
