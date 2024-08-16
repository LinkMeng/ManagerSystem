package com.linkmeng.managersystem.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
            IllegalArgumentException throwable = new IllegalArgumentException("Failed to convert object to JSON");
            throwable.setStackTrace(exception.getStackTrace());
            throw throwable;
        }
    }

    /**
     * 将Java对象序列化并写入文件
     *
     * @param object Java对象
     * @param file 文件
     * @param <T> Java对象的类型
     */
    public static <T> void toJson(T object, File file) {
        try {
            OBJECT_MAPPER.writeValue(file, object);
        } catch (IOException exception) {
            IllegalArgumentException throwable = new IllegalArgumentException("Failed to convert object to JSON");
            throwable.setStackTrace(exception.getStackTrace());
            throw throwable;
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
            IllegalArgumentException throwable = new IllegalArgumentException("Failed to convert JSON to object");
            throwable.setStackTrace(exception.getStackTrace());
            throw throwable;
        }
    }

    /**
     * 将文件中的JSON字符串反序列化成Java的Map对象
     *
     * @param file 文件
     * @param typeReference Map对象类型
     * @return Java的Map对象
     * @param <K> Key类型的泛型
     * @param <V> Value类型的泛型
     */
    public static <K, V> Map<K, V> fromJsonMap(File file, TypeReference<Map<K, V>> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(file, typeReference);
        } catch (IOException exception) {
            IllegalArgumentException throwable = new IllegalArgumentException("Failed to convert JSON to object");
            throwable.setStackTrace(exception.getStackTrace());
            throw throwable;
        }
    }
}
