package com.ProjectsManagementSystem.Aspect;

import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class AspectClass {

    private static final Logger logger = LoggerFactory.getLogger(AspectClass.class);

    @Before("execution(* com.ProjectsManagementSystem.project.*.*(..)) || execution(* com.ProjectsManagementSystem.task.*.*(..)) || execution(* com.ProjectsManagementSystem.comment.*.*(..)) " +
              "|| execution(* com.ProjectsManagementSystem.user.UserService.*(..)) " + "|| execution(* com.ProjectsManagementSystem.bug.*.*(..)) || execution(* com.ProjectsManagementSystem.auth.AuthenticationService.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method called : " + joinPoint.getSignature().toShortString());
    }

    @AfterThrowing("execution(* com.ProjectsManagementSystem.project.*.*(..)) || execution(* com.ProjectsManagementSystem.task.*.*(..)) || execution(* com.ProjectsManagementSystem.comment.*.*(..)) " +
            "|| execution(* com.ProjectsManagementSystem.user.UserService.*(..)) " + "|| execution(* com.ProjectsManagementSystem.bug.*.*(..)) || execution(* com.ProjectsManagementSystem.auth.AuthenticationService.*(..))")
    public void aspect(JoinPoint joinPoint) {
        logger.error("Exception thrown in method: {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* com.ProjectsManagementSystem.project.*.*(..)) || execution(* com.ProjectsManagementSystem.task.*.*(..)) || execution(* com.ProjectsManagementSystem.comment.*.*(..)) " +
            "|| execution(* com.ProjectsManagementSystem.user.UserService.*(..)) " + "|| execution(* com.ProjectsManagementSystem.bug.*.*(..)) || execution(* com.ProjectsManagementSystem.auth.AuthenticationService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            logger.info("Execution time of " + joinPoint.getSignature().toShortString() + " : " + duration + "ms");
        }
    }





}

