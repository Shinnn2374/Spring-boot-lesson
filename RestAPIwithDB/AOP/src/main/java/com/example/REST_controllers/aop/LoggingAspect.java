package com.example.REST_controllers.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect
{
    @Before("@annotation(Loggable)")
    public void logBefore(JoinPoint joinPoint)
    {
        log.info("Before execution of: {0}", joinPoint.getSignature().getName());
    }

    @After("@annotation(Loggable)")
    public void logAfter(JoinPoint joinPoint)
    {
        log.info("After execution of: {0}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@annotation(Loggable)",returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result)
    {
        log.info("AfterReturning execution of: {0} with result: {1}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "@annotation(Loggable)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception)
    {
        log.info("AfterThrowing execution of: {0} with execption: {1}", joinPoint.getSignature().getName(), exception.getMessage());
    }

    @Around("@annotation(Loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable
    {
        log.info("Around execution of: {0}", joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}
