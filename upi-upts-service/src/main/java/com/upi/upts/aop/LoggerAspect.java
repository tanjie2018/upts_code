package com.upi.upts.aop;

/*
 * @Author shixianglu
 * @Description TODO
 * @Date 11:20 2018/8/31
 * @Param
 * @return
 **/

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggerAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.upi.upts.aop.Logger)")
    private void cut() {
    }

    @Before("cut()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("Begin to execute operation：" + joinPoint.getSignature().getName());
        startTime.set(System.currentTimeMillis());
    }

    @After("cut()")
    public void doAfter(JoinPoint joinPoint) {
        float time = (System.currentTimeMillis() - startTime.get()) / 1000.00f;
        log.info("End execute operation: " + joinPoint.getSignature().getName() + ", with time lapse " + time + "s. ");
    }

}
