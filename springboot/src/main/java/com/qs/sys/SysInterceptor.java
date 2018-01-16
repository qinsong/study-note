package com.qs.sys;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/18.
 */
//@Aspect
@Component
public class SysInterceptor{
    private static final Logger logger = Logger.getLogger(SysInterceptor.class);

    /**
     * 定义拦截规则：拦截com.qs.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* com.qs.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}

    /**
     * 拦截器具体实现
     * @param pjp
     */
    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); //获取被拦截的方法
        String methodName = method.getName(); //获取被拦截的方法名

        Set<Object> allParams = new LinkedHashSet<>(); //保存所有请求参数，用于输出到日志中

        logger.info("开始请求方法："+ methodName);

        Object result = null;

        Object[] args = pjp.getArgs();
        for(Object arg : args){
            if (arg instanceof Map<?, ?>) {
                //提取方法中的MAP参数，用于记录进日志中
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) arg;

                allParams.add(map);
            }else if(arg instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest) arg;

                //获取query string 或 posted form data参数
                Map<String, String[]> paramMap = request.getParameterMap();
                if(paramMap!=null && paramMap.size()>0){
                    allParams.add(paramMap);
                }
            }else if(arg instanceof HttpServletResponse){
                //do nothing...
            }else{
                //do nothing
            }
        }

        try {
            if(result == null){
                // 一切正常的情况下，继续执行被拦截的方法
                result = pjp.proceed();
            }
        } catch (Throwable e) {
            logger.info("exception: ", e);
        }

        return result;
    }

}
