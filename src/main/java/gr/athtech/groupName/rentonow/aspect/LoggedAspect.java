package gr.athtech.groupName.rentonow.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggedAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedAspect.class);

    @Before("@annotation(logged)")
    public void logBefore(JoinPoint joinPoint, Logged logged) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] arguments = joinPoint.getArgs();
        Logger methodLogger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        methodLogger.info("{} BEGIN - {}", methodName, arguments);
    }

    @AfterReturning(value = "@annotation(logged)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Logged logged, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        Logger methodLogger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        if (result == null) {
            methodLogger.info("{} END - no result", methodName);
        } else {
            methodLogger.info("{} END - {}", methodName, result.toString());
        }
    }

    @AfterThrowing(value = "@annotation(logged)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Logged logged, Exception exception) {
        String methodName = joinPoint.getSignature().toShortString();
        String exceptionName = exception.getClass().getSimpleName();
        StackTraceElement[] errorStackTrace = exception.getStackTrace();
        Logger methodLogger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        methodLogger.error("{} ERROR - {} {} {}", exceptionName, methodName, exceptionName, errorStackTrace);
    }
}
