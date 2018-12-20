package com.upi.upts.aop;

import java.lang.annotation.*;
/*
 * @Author shixianglu
 * @Description TODO
 * @Date 11:19 2018/8/31
 * @Param
 * @return
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logger {
    String value() default "Logger4method.";
}
