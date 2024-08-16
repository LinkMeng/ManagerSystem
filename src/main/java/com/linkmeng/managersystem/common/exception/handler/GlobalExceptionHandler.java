package com.linkmeng.managersystem.common.exception.handler;

import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.common.exception.PermissionException;

import com.linkmeng.managersystem.model.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常信息处理类
 *
 * @since 2024-08-16
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 通用异常处理
     *
     * @param exception 通用异常
     * @return 异常信息
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionMessage> handleCommonException(CommonException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ExceptionMessage.of(exception), exception.getHttpStatus());
    }
}
