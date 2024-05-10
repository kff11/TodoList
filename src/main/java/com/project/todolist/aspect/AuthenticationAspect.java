package com.project.todolist.aspect;

import com.project.todolist.domain.user.service.UserService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class AuthenticationAspect {
    private final UserService userService;

    @Pointcut("execution(* com.project.todolist.domain.todo.controller.TodoController.*(..))")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void checkAuthentication(JoinPoint joinPoint) throws AuthException {
        Long userSeq = userService.getCurrentUserSeq();
        if (userSeq == null) {
            throw new AuthException("User not authenticated");
        }
    }
}
