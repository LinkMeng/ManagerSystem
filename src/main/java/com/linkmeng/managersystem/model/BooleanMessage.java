package com.linkmeng.managersystem.model;

import com.linkmeng.managersystem.common.util.ResourceUtil;

import lombok.Getter;

/**
 * 布尔返回信息模型
 *
 * @since 2024-08-17
 */
@Getter
public class BooleanMessage extends CommonMessage {
    /**
     * 操作结果
     */
    private final boolean value;

    /**
     * 构造方法
     *
     * @param value 操作结果
     * @param message 操作信息
     */
    private BooleanMessage(boolean value, String message) {
        super(message);
        this.value = value;
    }

    /**
     * 构造布尔返回信息模型
     *
     * @param value 操作结果
     * @param id 国际化Key
     * @param args 国际化参数
     * @return 布尔返回信息模型
     */
    public static BooleanMessage of(boolean value, String id, Object... args) {
        return new BooleanMessage(value, ResourceUtil.of(id, args));
    }
}
