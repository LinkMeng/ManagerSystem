package com.linkmeng.managersystem.common.cache;

import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.common.util.JsonUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源文件缓存类
 *
 * @since 2024-08-17
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceFileCache<K, V> {
    private static final long CACHE_REFRESH_TIME = TimeUnit.SECONDS.toMillis(5);

    /**
     * 缓存持久化线程池
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 文件访问锁
     */
    private final ReentrantLock targetFileLock = new ReentrantLock();

    /**
     * 目标文件路径
     */
    private final String targetFilePath;

    /**
     * 缓存
     */
    private Map<K, V> cache = new ConcurrentHashMap<>();

    /**
     * 缓存持久化任务
     */
    private ScheduledFuture<?> cachePersistenceTask;

    /**
     * 上次更新缓存时间
     */
    private volatile long lastRefreshTime = -1;

    /**
     * 从缓存中查询
     *
     * @param key 缓存Key
     * @return 缓存Value
     * @throws CommonException 抛出服务异常
     */
    public V get(K key) throws CommonException {
        readFromTargetFile();
        return cache.get(key);
    }

    /**
     * 从文件读取缓存
     *
     * @throws CommonException 抛出服务异常
     */
    private void readFromTargetFile() throws CommonException {
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastRefreshTime > CACHE_REFRESH_TIME) {
                doReadFile();
                lastRefreshTime = currentTime;
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            CommonException commonException = new CommonException(I18nConstant.RESOURCE_CACHE_FILE_CREATE_FAILED);
            commonException.setStackTrace(exception.getStackTrace());
            throw commonException;
        }
    }

    /**
     * 从文件读取缓存
     */
    private void doReadFile() {
        try (AutoCloseableLock ignored = new AutoCloseableLock(targetFileLock)) {
            File targetFile = getTargetFile(targetFilePath);
            Map<K, V> cacheFileMap = JsonUtil.fromJsonMap(targetFile, new TypeReference<Map<K, V>>() {});
            cache = new ConcurrentHashMap<>(cacheFileMap);
        } catch (CommonException exception) {
            log.error("Get cache file failed.", exception);
        }
    }

    /**
     * 更新到缓存
     *
     * @param key 缓存Key
     * @param value 缓存Value
     * @throws CommonException 抛出服务异常
     */
    public void put(K key, V value) throws CommonException {
        cache.put(key, value);
        lastRefreshTime = System.currentTimeMillis();
        writeToTargetFile();
    }

    /**
     * 将缓存写入到文件
     *
     * @throws CommonException 抛出服务异常
     */
    private void writeToTargetFile() throws CommonException {
        try {
            if (cachePersistenceTask != null && !cachePersistenceTask.isDone()) {
                cachePersistenceTask.cancel(false);
            }
            // 创建缓存持久化任务
            cachePersistenceTask = scheduler.schedule(this::doWriteFile, CACHE_REFRESH_TIME, TimeUnit.MILLISECONDS);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            CommonException commonException = new CommonException(I18nConstant.RESOURCE_CACHE_FILE_CREATE_FAILED);
            commonException.initCause(exception);
            throw commonException;
        }
    }

    /**
     * 将缓存写入到文件
     */
    private void doWriteFile() {
        try (AutoCloseableLock ignored = new AutoCloseableLock(targetFileLock)) {
            JsonUtil.toJson(cache, getTargetFile(targetFilePath));
        } catch (CommonException exception) {
            log.error("Read cache file failed.", exception);
        }
    }

    /**
     * 构造资源文件缓存
     *
     * @param targetFilePath 目标文件路径
     * @return 资源文件缓存
     * @throws CommonException 抛出服务异常
     * @param <K> 缓存的Key类型
     * @param <V> 缓存的Value类型
     */
    public static <K, V> ResourceFileCache<K, V> of(String targetFilePath) throws CommonException {
        getTargetFile(targetFilePath);
        return new ResourceFileCache<>(targetFilePath);
    }

    /**
     * 获取缓存目标文件对象
     *
     * @param targetFilePath 缓存目标文件路径
     * @return 缓存目标文件对象
     * @throws CommonException 抛出服务异常
     */
    private static File getTargetFile(String targetFilePath) throws CommonException {
        Path targetFilePathObj = Paths.get(targetFilePath);
        File targetFile = targetFilePathObj.toFile();
        if (targetFile.exists()) {
            if (!targetFile.isFile()) {
                log.error("Target file \"{}\" is not a file", targetFilePath);
                throw new CommonException(I18nConstant.RESOURCE_CACHE_FILE_PATH_ILLEGAL);
            }
            return targetFile;
        }
        try {
            Files.createDirectories(targetFilePathObj.getParent());
            Files.createFile(targetFilePathObj);
            return targetFile;
        } catch (IOException exception) {
            log.error("Create target file \"{}\" failed.", targetFilePath);
            CommonException commonException = new CommonException(I18nConstant.RESOURCE_CACHE_FILE_CREATE_FAILED);
            commonException.setStackTrace(exception.getStackTrace());
            throw commonException;
        }
    }
}
