package com.linkmeng.managersystem.common.exception;

import com.linkmeng.managersystem.common.constant.I18nConstant;
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
     * @param paramName 参数名称
     */
    public InputIllegalException(String paramName) {
        super(I18nConstant.COMMON_INPUT_PARAM_CHECK_FAILED, MESSAGE, paramName);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
