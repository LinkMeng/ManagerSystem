package com.linkmeng.managersystem.llt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * LLT反射工具类
 *
 * @since 2024-08-18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtil {

    /**
     * 设置静态属性
     *
     * @param clazz 目标类
     * @param fieldName 目标属性的名称
     * @param newValue 新的属性值
     * @param <T> 目标类的类型
     * @throws NoSuchFieldException 抛出异常
     * @throws IllegalArgumentException 抛出异常
     */
    public static <T> void setStaticField(Class<T> clazz, String fieldName, Object newValue)
        throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    /**
     * 获取静态属性
     *
     * @param clazz 目标类
     * @param fieldName 目标属性的名称
     * @param <T> 目标类的类型
     * @return 属性值
     * @throws NoSuchFieldException 抛出异常
     * @throws IllegalAccessException 抛出异常
     */
    public static <T> Object getStaticField(Class<T> clazz, String fieldName)
        throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(null);
    }
}
