package com.wisdom.mng.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class HttpAspect {

    private final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.wisdom.mng.controller.*.*(..))")
    public void log(){
    }

    /**
     * 方法开始执行
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("========================================================");
        //url
        log.info("请求接口:{}",request.getRequestURL());
        //method
        //log.info("请求方式:{}",request.getMethod());
        //ip
        log.info("请求ip:{}",request.getRemoteAddr());
        //class_method
        log.info("请求方法:{}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //args
        log.info("请求参数:{}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 方法返回执行
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "log()")
    @ResponseBody
    public void doAfterReturning(Object ret){
        // 处理完请求，返回内容
        log.info("返回内容:{}", JSON.toJSONString(ret));
        log.info("========================================================");
    }

    //方法执行异常执行
    @AfterThrowing("log()")
    public void doAfterThrowing(JoinPoint joinPoint){
        log.info("执行异常:{}", joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
    }
}