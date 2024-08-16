package com.linkmeng.managersystem.role;

import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.common.exception.PermissionException;
import com.linkmeng.managersystem.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限校验切面
 *
 * @since 2024-08-16
 */
@Slf4j
@Aspect
@Component
public class RoleCheckAspect {
    private static final String USER_INFO_HEADER_NAME = "User-Info";

    /**
     * 校验用户权限并设置用户信息
     *
     * @param joinPoint 切点
     * @return 目标方法执行结果
     * @throws Throwable 抛出服务异常
     */
    @Around("@annotation(com.linkmeng.managersystem.role.RequiredUserRole)")
    public Object checkRoleAndParseUserId(ProceedingJoinPoint joinPoint) throws Throwable {
        User currentUser = getUser();
        if (currentUser == null) {
            log.error("Failed to get user info from request header.");
            throw new PermissionException(I18nConstant.COMMON_ROLE_USER_NOT_FOUND);
        }
        if (!getExpectedRoles(joinPoint).contains(currentUser.getRole())) {
            log.warn("User {} permission denied.", currentUser.getUserId());
            throw new PermissionException(I18nConstant.COMMON_ROLE_USER_PERMISSION_DENIED, currentUser.getUserId());
        }
        return joinPoint.proceed(fillUserInfo(joinPoint, currentUser));
    }

    /**
     * 从请求头中获取用户信息
     *
     * @return 用户信息
     * @throws CommonException 抛出服务异常
     */
    private User getUser() throws CommonException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String headerValue = request.getHeader(USER_INFO_HEADER_NAME);
        return StringUtils.isEmpty(headerValue) ? null : User.ofBase64(headerValue);
    }

    /**
     * 获取目标方法声明的用户权限
     *
     * @param joinPoint 切点
     * @return 用户权限集合
     */
    private Set<User.Role> getExpectedRoles(ProceedingJoinPoint joinPoint) {
        Method objMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RequiredUserRole requiredUserRole = objMethod.getAnnotation(RequiredUserRole.class);
        return new HashSet<>(Arrays.asList(requiredUserRole.value()));
    }

    /**
     * 向目标方法参数中填充用户信息
     *
     * @param joinPoint 切点
     * @param currentUser 用户信息
     * @return 参数列表
     */
    private Object[] fillUserInfo(ProceedingJoinPoint joinPoint, User currentUser) {
        Method objMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = objMethod.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getAnnotation(RoleUserInfo.class) != null) {
                args[i] = currentUser;
            }
        }
        return args;
    }
}
