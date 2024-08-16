package com.linkmeng.managersystem.common.exception;

import com.linkmeng.managersystem.common.exception.handler.HttpStatusException;
import org.springframework.http.HttpStatus;

/**
 * 权限相关异常
 *
 * @since 2024-08-16
 */
public class PermissionException extends CommonException implements HttpStatusException {
    private static final String MESSAGE = "Permission Denied";

    /**
     * 构造方法
     *
     * @param id 国际化ID
     * @param args 国际化参数
     */
    public PermissionException(String id, Object... args) {
        super(id, MESSAGE, args);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
