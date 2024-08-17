package com.linkmeng.managersystem.llt;

import lombok.Getter;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Path;

/**
 * 缓存文件Mock
 *
 * @since 2024-08-18
 */
public class CacheFileMock implements AutoCloseable {
    private final File fileMock;

    @Getter
    private final Path pathMock;

    /**
     * 构造方法
     */
    public CacheFileMock() {
        fileMock = Mockito.mock(File.class);
        Mockito.when(fileMock.exists()).thenReturn(true);
        Mockito.when(fileMock.isFile()).thenReturn(true);
        pathMock = Mockito.mock(Path.class);
        Mockito.when(pathMock.toFile()).thenReturn(fileMock);
    }

    @Override
    public void close() {
        Mockito.reset(fileMock, pathMock);
    }
}
