package com.librarymanagement.library_management.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.librarymanagement.library_management.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        logger.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);

        return result;
    }

    @Before("execution(* com.librarymanagement.library_management.service.*.*(..))")
    public void logBeforeMethodCall() {
        logger.info("Method is being executed...");
    }

    @AfterThrowing(pointcut = "execution(* com.librarymanagement.library_management.service.*.*(..))", throwing = "ex")
    public void logExceptions(Exception ex) {
        logger.error("Exception occurred: {}", ex.getMessage());
    }
}
