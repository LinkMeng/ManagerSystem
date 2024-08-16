package com.linkmeng.managersystem.common.exception.handler;

import org.springframework.http.HttpStatus;

/**
 * 获取异常信息对应的HTTP状态码
 *
 * @since 2024-08-16
 */
public interface HttpStatusException {
    /**
     * 获取异常信息对应的HTTP状态码
     *
     * @return HTTP状态码
     */
    HttpStatus getHttpStatus();
}
