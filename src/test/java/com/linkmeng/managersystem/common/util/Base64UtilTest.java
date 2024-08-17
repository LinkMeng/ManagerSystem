package com.linkmeng.managersystem.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base64UtilTest {

    @Test
    public void test_decode_normal() {
        // Given
        String base64 = "SGVsbG8sIFdvcmxkIQ==";
        // When
        String actual = Base64Util.decode(base64);
        // Then
        Assertions.assertEquals("Hello, World!", actual);
    }

    @Test
    public void test_decode_failed() {
        String base64 = "==";
        Assertions.assertThrows(IllegalArgumentException.class, () -> Base64Util.decode(base64));
    }
}
