package com.linkmeng.managersystem.common.util;

import com.linkmeng.managersystem.common.constant.I18nConstant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Locale;

public class ResourceUtilTest {

    @Test
    public void test_of_normal() {
        try (MockedStatic<Locale> mockedStatic = Mockito.mockStatic(Locale.class)) {
            mockedStatic.when(Locale::getDefault).thenReturn(Locale.US);
            String message = ResourceUtil.of(I18nConstant.MESSAGE_ADMIN_ADD_USER_SUCCESS);
            Assertions.assertEquals("Successfully configured {1} resources for user {0}.", message);
        }
    }

    @Test
    public void test_of_inputNull() {
        String message = ResourceUtil.of(null);
        Assertions.assertEquals("", message);
    }

    @Test
    public void test_of_inputEmpty() {
        String message = ResourceUtil.of("");
        Assertions.assertEquals("", message);
    }

    @Test
    public void test_of_inputNonExistedKey() {
        String message = ResourceUtil.of("non.existed.key");
        Assertions.assertEquals("!!non.existed.key!!", message);
    }
}
