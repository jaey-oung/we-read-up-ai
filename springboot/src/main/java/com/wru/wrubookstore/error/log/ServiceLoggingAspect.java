package com.wru.wrubookstore.error.log;

import com.wru.wrubookstore.error.exception.DebuggableException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

@Component
@Aspect
public class ServiceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    // 서비스 진입 로그
    @Before("execution(* com.wru.wrubookstore.service.*.*(..))")
    public void logServiceAccess(JoinPoint joinPoint){
        log.info("➡ [서비스 진입] {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    // 예외 발생시 로그
    @AfterThrowing(pointcut="execution(* com.wru.wrubookstore.service.*.*(..))", throwing = "e")
    public void logServiceError(JoinPoint joinPoint, Exception e){

        if(e instanceof DebuggableException de){
            log.error("\uD83D\uDD25 [예외 발생] {}.{}() - 디버그: {}, 메시지: {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    de.getDebugMessage(),
                    e.getMessage(), e);
        } else {
            log.error("\uD83D\uDD25 [예외 발생] {}.{}() - 메시지: {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getMessage(), e);
        }

    }
}
