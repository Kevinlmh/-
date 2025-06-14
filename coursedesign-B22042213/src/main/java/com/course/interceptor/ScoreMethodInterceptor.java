package com.course.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
@Component("scoreMethodInterceptor")
public class ScoreMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("before method invoke....");

        // 执行被拦截的方法（一般是Controller的方法，Controller内部调用Service）
        Object result = methodInvocation.proceed();

        System.out.println("after method invoke.....");
        return result;
    }
}
