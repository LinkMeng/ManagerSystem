package com.linkmeng.managersystem.common.util;

import com.linkmeng.managersystem.llt.ReflectionUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtilTest {

    @Test
    void test_toJson_normal() {
        String expectedJson = "{\"key\":\"value\"}";
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        String actualJson = JsonUtil.toJson(map);
        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    void test_toJson_throwsNotIOException() throws Exception {
        Object originalObjectMapper = ReflectionUtil.getStaticField(JsonUtil.class, "OBJECT_MAPPER");

        try {
            ObjectMapper mockMapper = Mockito.mock(ObjectMapper.class);
            Mockito.when(mockMapper.writeValueAsString(Mockito.any()))
                .thenThrow(new JsonProcessingException("Mock Exception") {});
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", mockMapper);

            JsonUtil.toJson(new HashMap<>());
            Assertions.fail();
        } catch (Exception exception) {
            Assertions.assertInstanceOf(IllegalArgumentException.class, exception);
            Assertions.assertNull(exception.getCause());
        } finally {
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", originalObjectMapper);
        }
    }

    @Test
    void test_toJsonFile_throwsNotIOException() throws Exception {
        Object originalObjectMapper = ReflectionUtil.getStaticField(JsonUtil.class, "OBJECT_MAPPER");
        File tempFile = File.createTempFile("temp", ".json");

        try {
            ObjectMapper mockMapper = Mockito.mock(ObjectMapper.class);
            Mockito.doThrow(new IOException("Mock Exception")).when(mockMapper)
                .writeValue(Mockito.any(File.class), Mockito.any());
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", mockMapper);

            JsonUtil.toJson(new HashMap<>(), tempFile);
            Assertions.fail();
        } catch (Exception exception) {
            Assertions.assertInstanceOf(IllegalArgumentException.class, exception);
            Assertions.assertNull(exception.getCause());
        } finally {
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", originalObjectMapper);
            tempFile.delete();
        }
    }

    @Test
    void test_fromJson_throwsNotIOException() throws Exception {
        Object originalObjectMapper = ReflectionUtil.getStaticField(JsonUtil.class, "OBJECT_MAPPER");

        try {
            ObjectMapper mockMapper = Mockito.mock(ObjectMapper.class);
            Mockito.when(mockMapper.readValue(Mockito.anyString(), Mockito.any(Class.class)))
                .thenThrow(new JsonProcessingException("Mock Exception") {});
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", mockMapper);

            JsonUtil.fromJson("{\"key\":\"value\"}", Object.class);
            Assertions.fail();
        } catch (Exception exception) {
            Assertions.assertInstanceOf(IllegalArgumentException.class, exception);
            Assertions.assertNull(exception.getCause());
        } finally {
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", originalObjectMapper);
        }
    }

    @Test
    void test_fromJsonMap_throwsNotIOException() throws Exception {
        Object originalObjectMapper = ReflectionUtil.getStaticField(JsonUtil.class, "OBJECT_MAPPER");
        File tempFile = File.createTempFile("temp", ".json");

        try {
            ObjectMapper mockMapper = Mockito.mock(ObjectMapper.class);
            Mockito.when(mockMapper.readValue(Mockito.any(File.class), Mockito.any(TypeReference.class)))
                .thenThrow(new IOException("Mock Exception") {});
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", mockMapper);

            JsonUtil.fromJsonMap(tempFile, new TypeReference<Map<String, Object>>() {});
            Assertions.fail();
        } catch (Exception exception) {
            Assertions.assertInstanceOf(IllegalArgumentException.class, exception);
            Assertions.assertNull(exception.getCause());
        } finally {
            ReflectionUtil.setStaticField(JsonUtil.class, "OBJECT_MAPPER", originalObjectMapper);
            tempFile.delete();
        }
    }

}
