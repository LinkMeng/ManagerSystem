package com.linkmeng.managersystem.common;

import com.linkmeng.managersystem.common.constant.I18nConstant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest
public class ResourceUtilTest {

    @Test
    public void test_of_normal() {
        try (MockedStatic<Locale> mockedStatic = Mockito.mockStatic(Locale.class)) {
            mockedStatic.when(Locale::getDefault).thenReturn(Locale.US);
            String message = ResourceUtil.of(I18nConstant.I18N_FORMAT_GET_FILE + ".message");
            Assertions.assertEquals("!!i18n.format.getFile.message!!", message);
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
