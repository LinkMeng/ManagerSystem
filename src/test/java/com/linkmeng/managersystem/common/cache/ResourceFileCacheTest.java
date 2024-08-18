package com.linkmeng.managersystem.common.cache;

import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.llt.CacheFileMock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceFileCacheTest {
    @Test
    public void test_getTargetFile_normalFileExist() {
        try (CacheFileMock cacheFileMock = new CacheFileMock();
             MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class)) {
            // Given
            pathsMock.when(() -> Paths.get(Mockito.anyString())).thenReturn(cacheFileMock.getPathMock());
            // When
            ResourceFileCache<String, Object> testFile = ResourceFileCache.of("testFile");
            // Then
            Assertions.assertNotNull(testFile);
        } catch (CommonException exception) {
            Assertions.fail(exception.getMessage());
        }
    }

    @Test
    public void test_getTargetFile_normalFileNotExist() {
        try (CacheFileMock cacheFileMock = new CacheFileMock(false, false);
             MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class);
             MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            // Given
            pathsMock.when(() -> Paths.get(Mockito.anyString())).thenReturn(cacheFileMock.getPathMock());
            filesMock.when(() -> Files.createDirectories(Mockito.any(Path.class))).thenReturn(null);
            filesMock.when(() -> Files.createFile(Mockito.any(Path.class))).thenReturn(null);
            filesMock.when(() -> Files.write(Mockito.any(Path.class), Mockito.any(byte[].class))).thenReturn(null);
            // When
            ResourceFileCache<String, Object> testFile = ResourceFileCache.of("testFile");
            // Then
            Assertions.assertNotNull(testFile);
        } catch (CommonException exception) {
            Assertions.fail(exception.getMessage());
        }
    }

    @Test
    public void test_getTargetFile_exceptionNotFile() {
        try (CacheFileMock cacheFileMock = new CacheFileMock(true, false);
             MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class)) {
            // Given
            pathsMock.when(() -> Paths.get(Mockito.anyString())).thenReturn(cacheFileMock.getPathMock());
            // When
            ResourceFileCache.of("testFile");
            // Then
            Assertions.fail();
        } catch (CommonException exception) {
            Assertions.assertEquals(I18nConstant.RESOURCE_CACHE_FILE_PATH_ILLEGAL, exception.getExceptionId());
            Assertions.assertNull(exception.getCause());
        }
    }

    @Test
    public void test_getTargetFile_exceptionFileCreateFailed() {
        try (CacheFileMock cacheFileMock = new CacheFileMock(false, false);
             MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class);
             MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            // Given
            pathsMock.when(() -> Paths.get(Mockito.anyString())).thenReturn(cacheFileMock.getPathMock());
            filesMock.when(() -> Files.createDirectories(Mockito.any(Path.class))).thenReturn(null);
            filesMock.when(() -> Files.createFile(Mockito.any(Path.class))).thenThrow(IOException.class);
            // When
            ResourceFileCache.of("testFile");
            // Then
            Assertions.fail();
        } catch (CommonException exception) {
            Assertions.assertEquals(I18nConstant.RESOURCE_CACHE_FILE_CREATE_FAILED, exception.getExceptionId());
            Assertions.assertNull(exception.getCause());
        }
    }
}
