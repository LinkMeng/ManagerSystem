package com.linkmeng.managersystem.model;

import com.linkmeng.managersystem.common.util.ResourceUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 布尔返回信息模型
 *
 * @since 2024-08-17
 */
@Getter
@AllArgsConstructor
public class CommonMessage {
    /**
     * 操作结果
     */
    private final boolean value;

    /**
     * 操作信息
     */
    private final String message;

    /**
     * 构造布尔返回信息模型
     *
     * @param value 操作结果
     * @param id 国际化Key
     * @param args 国际化参数
     * @return 布尔返回信息模型
     */
    public static CommonMessage of(boolean value, String id, Object... args) {
        return new CommonMessage(value, ResourceUtil.of(id, args));
    }
}
