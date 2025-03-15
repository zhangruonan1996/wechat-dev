package org.zhangruonan.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-17 19:21:14
 */
@Component
@Slf4j
@Aspect
public class ServiceLogAspect {

    @Around("execution(* org.zhangruonan.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 需要统计每一个service实现的执行时间，如果执行的时间太久，则进行error级别的日志输出
        // Long begin = System.currentTimeMillis();
        StopWatch stopWatch = new StopWatch();

        String pointName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        stopWatch.start("执行主业务：" + pointName);

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        // stopWatch.start("执行其他业务1001");
        // Thread.sleep(250);
        // stopWatch.stop();
        //
        // stopWatch.start("执行其它业务2002");
        // Thread.sleep(350);
        // stopWatch.stop();

        log.info(stopWatch.prettyPrint());
        log.info(stopWatch.shortSummary());
        log.info("任务总数：{}", stopWatch.getTaskCount());
        log.info("任务执行总时间：{}毫秒", stopWatch.getTotalTimeMillis());

        // Long end = System.currentTimeMillis();
        Long takeTimes = stopWatch.getTotalTimeMillis();

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
