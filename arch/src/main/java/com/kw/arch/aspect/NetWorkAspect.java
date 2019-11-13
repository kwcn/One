package com.kw.arch.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Kang Wei
 * @date 2019/11/12
 */
@Aspect
public class NetWorkAspect {
    @Pointcut("execution(@com.kw.arch.aspect.CheckNet  * *(..))")
    public void executionCheckNet() {
    }

    @Around("executionCheckNet()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            NetWorkAdapter netWorkAdapter = NetWorkAdapter.getInstance();
            if (netWorkAdapter != null && netWorkAdapter.getConnect() != null) {
                if (netWorkAdapter.getConnect().onConnect()) {
                    return joinPoint.proceed();
                } else {
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }
}
