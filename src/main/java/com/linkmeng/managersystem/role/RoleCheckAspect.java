package com.linkmeng.managersystem.role;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;

@Aspect
@Component
public class RoleCheckAspect {
    @Around("@annotation(RequiredRole)")
    public Object checkRoleAndParseUserId(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request1 = attributes.getRequest();
        Enumeration<String> headerNames = request1.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request1.getHeader(headerName);
            System.out.println(headerName + ":" + headerValue);
        }

        RequiredRole requiredRole = getDeclaredAnnotation(joinPoint);
        Arrays.stream(requiredRole.value()).forEach(role -> System.out.println(role.getRoleName()));

        Object[] args = joinPoint.getArgs();
        args[1] = 114514;
        return joinPoint.proceed(args);
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint 切入点
     * @return 获取被切入方法上的注解
     * @throws NoSuchMethodException 找不到方法
     */
    public RequiredRole getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        return objMethod.getDeclaredAnnotation(RequiredRole.class);
    }
}
