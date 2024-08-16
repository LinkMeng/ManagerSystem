package com.linkmeng.managersystem.model;

import com.linkmeng.managersystem.common.Base64Util;
import com.linkmeng.managersystem.common.JsonUtil;
import com.linkmeng.managersystem.common.exception.CommonException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class UserTest {

    @Test
    public void test_ofBase64_normal() throws CommonException {
        // Data
        User expectecUser = User.builder().userId(123456).accountName("XXXXXXX").role(User.Role.ADMIN).build();
        String userBase64Str = Base64.getEncoder().encodeToString(JsonUtil.toJson(expectecUser).getBytes());
        // When
        User actualUser = User.ofBase64(userBase64Str);
        // Then
        Assertions.assertEquals(expectecUser, actualUser);
    }
}
