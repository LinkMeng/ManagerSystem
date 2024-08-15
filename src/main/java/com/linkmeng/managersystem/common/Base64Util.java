package com.linkmeng.managersystem.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Base64;

/**
 * 字符串转Base64编码工具类
 *
 * @since 2024-08-15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Base64Util {
    /**
     * 解码Base64字符串
     *
     * @param base64 Base64字符串
     * @return 解码后的字符串
     */
    public static String decode(String base64) {
        return new String(Base64.getDecoder().decode(base64));
    }
}
