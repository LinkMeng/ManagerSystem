package com.linkmeng.managersystem.common.exception;

import com.linkmeng.managersystem.common.exception.handler.HttpStatusException;
import org.springframework.http.HttpStatus;

/**
 * 非法用户输入异常
 *
 * @since 20024-08-16
 */
public class InputIllegalException extends CommonException implements HttpStatusException {
    private static final String MESSAGE = "Illegal Input Param";

    /**
     * 构造方法
     *
     * @param id 国际化ID
     * @param args 国际化参数
     */
    public InputIllegalException(String id, Object... args) {
        super(id, MESSAGE, args);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
