package com.linkmeng.managersystem.role;

import com.linkmeng.managersystem.model.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色校验切面注解
 *
 * @since 2024-08-16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequiredRole {
    /**
     * 允许访问的用户角色
     *
     * @return 角色列表
     */
    User.Role[] value() default {};
}
