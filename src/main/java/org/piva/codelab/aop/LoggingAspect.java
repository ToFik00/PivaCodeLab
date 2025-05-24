package org.piva.codelab.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@Order(1)
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {}

    @Before("controller()")
    public void logBefore(JoinPoint jp) {
        log.info("Вход в {}.{}(), args = {}",
                jp.getSignature().getDeclaringType().getSimpleName(),
                jp.getSignature().getName(),
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "controller()", returning = "result")
    public void logAfter(JoinPoint jp, Object result) {
        log.info("Выход из {}.{}(), return = {}",
                jp.getSignature().getDeclaringType().getSimpleName(),
                jp.getSignature().getName(),
                result);
    }
}

