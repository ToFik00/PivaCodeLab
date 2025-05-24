package org.piva.codelab.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@Order(2)
public class PerformanceAspect {

    @Around("execution(* org.piva.codelab.service..*(..))")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long duration = System.nanoTime() - start;
            log.info("{} выполнен за {} мс",
                    pjp.getSignature(), TimeUnit.NANOSECONDS.toMillis(duration));
        }
    }
}

