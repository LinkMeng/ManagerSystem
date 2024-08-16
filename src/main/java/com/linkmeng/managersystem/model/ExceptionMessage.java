package com.linkmeng.managersystem.model;

import com.linkmeng.managersystem.common.ResourceUtil;

import com.linkmeng.managersystem.common.exception.CommonException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

/**
 * 抛异常时的返回体
 *
 * @since 2024-08-16
 */
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExceptionMessage {
    private static final String SUFFIX_MESSAGE = ".message";
    private static final String SUFFIX_DETAIL = ".detail";
    private static final String SUFFIX_SUGGESTION = ".suggestion";

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 错误详情
     */
    private final String detail;

    /**
     * 修复建议
     */
    private final String suggestion;

    /**
     * 构造异常返回体
     *
     * @param commonException 异常信息
     * @return 异常返回体
     */
    public static ExceptionMessage of(CommonException commonException) {
        return of(commonException.getExceptionId(), commonException.getArguments());
    }

    /**
     * 构造异常返回体
     *
     * @param id 国际化ID
     * @param args 参数
     * @return 异常返回体
     */
    public static ExceptionMessage of(String id, Object... args) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return ExceptionMessage.builder()
            .message(ResourceUtil.of(id + SUFFIX_MESSAGE, args))
            .detail(ResourceUtil.of(id + SUFFIX_DETAIL, args))
            .suggestion(ResourceUtil.of(id + SUFFIX_SUGGESTION, args))
            .build();
    }
}
