package com.linkmeng.managersystem.model;

import com.linkmeng.managersystem.common.util.ResourceUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公共返回信息模型
 *
 * @since 2024-08-17
 */
@Getter
@AllArgsConstructor
public class CommonMessage {
    /**
     * 操作信息
     */
    private final String message;

    /**
     * 构造公共返回信息模型
     *
     * @param id 国际化Key
     * @param args 国际化参数
     * @return 公共返回信息模型
     */
    public static CommonMessage of(String id, Object... args) {
        return new CommonMessage(ResourceUtil.of(id, args));
    }
}
