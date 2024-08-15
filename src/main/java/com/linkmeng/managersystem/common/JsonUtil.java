package com.linkmeng.managersystem.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * JSON字符串与对象互转工具类
 *
 * @since 2024-08-15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Java对象序列化成JSON字符串
     *
     * @param object Java对象
     * @return JSON字符串
     * @param <T> Java对象的类型
     */
    public static <T> String toJson(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException(exception); // TODO
        }
    }

    /**
     * JSON字符串反序列化成Java对象
     *
     * @param json JSON字符串
     * @param clazz Java对象的类型
     * @return Java对象
     * @param <T> Java对象的类型
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException(exception); // TODO
        }
    }
}
