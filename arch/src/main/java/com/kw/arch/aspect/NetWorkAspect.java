package com.kw.arch.aspect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import static android.content.Context.CONNECTIVITY_SERVICE;

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
            ICheckNet iCheckNet = (ICheckNet) joinPoint.getThis();
            if (getCurNetWorkStatus(iCheckNet.onContext())) {
                return joinPoint.proceed();
            } else {
                iCheckNet.onFailNet();
                return null;
            }
        }
        return joinPoint.proceed();
    }

    private boolean getCurNetWorkStatus(Context context) {
        NetworkInfo activeNetworkInfo =
                ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            Toast.makeText(context, "无网络连接，请检查网络", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
