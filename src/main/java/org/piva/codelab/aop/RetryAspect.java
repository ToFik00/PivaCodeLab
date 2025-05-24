package org.piva.codelab.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.piva.codelab.annotation.RetryableOperation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class RetryAspect {

    @Around("@annotation(retryable)")
    public Object aroundRetry(ProceedingJoinPoint pjp, RetryableOperation retryable) throws Throwable {
        int maxAttempts = retryable.maxAttempts();
        long delay = retryable.delay();
        Throwable lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return pjp.proceed();
            } catch (Throwable ex) {
                lastException = ex;
                if (attempt == maxAttempts) {
                    throw lastException;
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw ie;
                }
            }
        }
        throw new IllegalStateException("RetryAspect reached an unexpected state", lastException);
    }
}
