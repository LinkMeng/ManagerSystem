package com.linkmeng.managersystem.controller;

import com.linkmeng.managersystem.common.util.JsonUtil;
import com.linkmeng.managersystem.model.User;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_authCheck_normal() throws Exception {
        User user = User.builder().userId(123456).accountName("X").role(User.Role.USER).build();
        String baseCode = Base64.getEncoder().encodeToString(JsonUtil.toJson(user).getBytes());
        String resourceName = "resource A";

        Map<String, Object> cache = buildMockedCache();

        try (MockedStatic<JsonUtil> jsonUtilMock = Mockito.mockStatic(JsonUtil.class)) {
            jsonUtilMock.when(() -> JsonUtil.fromJsonMap(Mockito.any(File.class), Mockito.any())).thenReturn(cache);
            jsonUtilMock.when(() -> JsonUtil.fromJson(Mockito.anyString(), Mockito.any())).thenReturn(user);
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/user/" + resourceName)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("User-Info", baseCode))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(true));
        }
    }

    @Test
    public void test_authCheck_normalFailed() throws Exception {
        User user = User.builder().userId(123456).accountName("X").role(User.Role.USER).build();
        String baseCode = Base64.getEncoder().encodeToString(JsonUtil.toJson(user).getBytes());
        String resourceName = "resource D";

        Map<String, Object> cache = buildMockedCache();

        try (MockedStatic<JsonUtil> jsonUtilMock = Mockito.mockStatic(JsonUtil.class)) {
            jsonUtilMock.when(() -> JsonUtil.fromJsonMap(Mockito.any(File.class), Mockito.any())).thenReturn(cache);
            jsonUtilMock.when(() -> JsonUtil.fromJson(Mockito.anyString(), Mockito.any())).thenReturn(user);
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/user/" + resourceName)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("User-Info", baseCode))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(false));
        }
    }

    @Test
    public void test_authCheck_forbidden() throws Exception {
        User user = User.builder().userId(123456).accountName("X").role(User.Role.USER).build();
        String resourceName = "resource D";

        Map<String, Object> cache = buildMockedCache();

        try (MockedStatic<JsonUtil> jsonUtilMock = Mockito.mockStatic(JsonUtil.class)) {
            jsonUtilMock.when(() -> JsonUtil.fromJsonMap(Mockito.any(File.class), Mockito.any())).thenReturn(cache);
            jsonUtilMock.when(() -> JsonUtil.fromJson(Mockito.anyString(), Mockito.any())).thenReturn(user);
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/user/" + resourceName)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suggestion").isString());
        }
    }

    @Test
    public void test_authCheck_internalServerError() throws Exception {
        User user = User.builder().userId(123456).accountName("X").role(User.Role.USER).build();
        String resourceName = "resource D";

        Map<String, Object> cache = buildMockedCache();

        try (MockedStatic<JsonUtil> jsonUtilMock = Mockito.mockStatic(JsonUtil.class)) {
            jsonUtilMock.when(() -> JsonUtil.fromJsonMap(Mockito.any(File.class), Mockito.any())).thenReturn(cache);
            jsonUtilMock.when(() -> JsonUtil.fromJson(Mockito.anyString(), Mockito.any())).thenReturn(user);
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/user/" + resourceName)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("User-Info", "e==="))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suggestion").isString());
        }
    }

    /**
     * 获取缓存Mock
     *
     * @return 缓存Mock
     * @throws Exception 抛出异常
     */
    private static Map<String, Object> buildMockedCache() throws Exception {
        String className = "com.linkmeng.managersystem.common.cache.ResourceFileCache$RecordValue";
        Class<?> innerClass = Class.forName(className);
        Constructor<?> constructor = innerClass.getDeclaredConstructor(long.class, Object.class);
        constructor.setAccessible(true);
        Object instance = constructor.newInstance(Long.MAX_VALUE, Collections.singletonList("resource A"));

        Map<String, Object> cache = new HashMap<>();
        cache.put("123456", instance);
        return cache;
    }
}
