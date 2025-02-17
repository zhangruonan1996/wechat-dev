package com.dengenxi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-17 19:21:14
 */
@Component
@Slf4j
@Aspect
public class ServiceLogAspect {

    @Around("execution(* com.dengenxi.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 需要统计每一个service实现的执行时间，如果执行的时间太久，则进行error级别的日志输出
        Long begin = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();
        String pointName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        Long end = System.currentTimeMillis();
        Long takeTimes = end - begin;

        if (takeTimes > 3000) {
            log.error("执行位置：{}，执行时间过久，本次执行耗费{}毫秒", pointName, takeTimes);
        } else if (takeTimes > 2000) {
            log.warn("执行位置：{}，执行时间稍久，本次执行耗费了{}毫秒", pointName, takeTimes);
        } else {
            log.info("执行位置：{}，执行时间正常，本次执行耗费了{}毫秒", pointName, takeTimes);
        }

        return proceed;
    }

}
