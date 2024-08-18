package com.linkmeng.managersystem.model;

import com.linkmeng.managersystem.common.exception.InputIllegalException;
import com.linkmeng.managersystem.common.util.JsonUtil;
import com.linkmeng.managersystem.common.exception.CommonException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class UserTest {

    @Test
    public void test_ofBase64_normal() throws CommonException {
        // Given
        User expectecUser = User.builder().userId(123456).accountName("XXXXXXX").role(User.Role.ADMIN).build();
        String userBase64Str = Base64.getEncoder().encodeToString(JsonUtil.toJson(expectecUser).getBytes());
        // When
        User actualUser = User.ofBase64(userBase64Str);
        // Then
        Assertions.assertEquals(expectecUser, actualUser);
    }

    @Test
    public void test_ofBase64_illegalBase64() {
        // Given
        String userBase64Str = "e===";
        // When, Then
        Assertions.assertThrows(CommonException.class, () -> User.ofBase64(userBase64Str));
    }

    @Test
    public void test_ofBase64_illegalInputUser() {
        // Given
        String userBase64Str = "bnVsbA==";
        // When, Then
        InputIllegalException exception =
            Assertions.assertThrows(InputIllegalException.class, () -> User.ofBase64(userBase64Str));
        Assertions.assertEquals(1, exception.getArguments().length);
        Assertions.assertEquals("user", exception.getArguments()[0]);
    }

    @Test
    public void test_ofBase64_illegalInputUserId() {
        // Given
        String userBase64Str = "eyJ1c2VySWQiOiAtMSwgImFjY291bnROYW1lIjogIlhYWFhYWFgiLCAicm9sZSI6ICJhZG1pbiJ9";
        // When, Then
        InputIllegalException exception =
            Assertions.assertThrows(InputIllegalException.class, () -> User.ofBase64(userBase64Str));
        Assertions.assertEquals(1, exception.getArguments().length);
        Assertions.assertEquals("user.userId", exception.getArguments()[0]);
    }

    @Test
    public void test_ofBase64_illegalInputAccountName() {
        // Given
        String userBase64Str = "eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICIiLCAicm9sZSI6ICJhZG1pbiJ9";
        // When, Then
        InputIllegalException exception =
            Assertions.assertThrows(InputIllegalException.class, () -> User.ofBase64(userBase64Str));
        Assertions.assertEquals(1, exception.getArguments().length);
        Assertions.assertEquals("user.accountName", exception.getArguments()[0]);
    }

    @Test
    public void test_ofBase64_illegalInputRole() {
        // Given
        String userBase64Str = "eyJ1c2VySWQiOiAxMjM0NTYsICJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIn0=";
        // When, Then
        InputIllegalException exception =
            Assertions.assertThrows(InputIllegalException.class, () -> User.ofBase64(userBase64Str));
        Assertions.assertEquals(1, exception.getArguments().length);
        Assertions.assertEquals("user.role", exception.getArguments()[0]);
    }
}
