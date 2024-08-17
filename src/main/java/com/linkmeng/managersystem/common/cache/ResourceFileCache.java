package com.linkmeng.managersystem.common.cache;

import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.common.util.JsonUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private static final long CACHE_REFRESH_TIME = TimeUnit.SECONDS.toMillis(10);

    /**
     * 资源文件缓存记录
     *
     * @since 2024-08-17
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static final class RecordValue<V> {
        /**
         * 更新时间
         */
        private long updatedTime;

        /**
         * 记录值
         */
        private V value;
    }

    /**
     * 缓存持久化线程池
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 文件读写和缓存更新锁
     */
    private final ReentrantLock targetFileAndCacheLock = new ReentrantLock();

    /**
     * 目标文件路径
     */
    private final String targetFilePath;

    /**
     * 缓存
     */
    private final Map<K, RecordValue<V>> cache = new ConcurrentHashMap<>();

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
        RecordValue<V> recordValue = cache.get(key);
        return recordValue == null ? null : recordValue.getValue();
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
        try (AutoCloseableLock ignored = new AutoCloseableLock(targetFileAndCacheLock)) {
            File targetFile = getTargetFile(targetFilePath);
            JsonUtil.fromJsonMap(targetFile, new TypeReference<Map<K, RecordValue<V>>>() {})
                .forEach((key, value) -> cache.merge(key, value, (oldVal, newVal) ->
                    newVal.getUpdatedTime() > oldVal.getUpdatedTime() ? newVal : oldVal));
            cache.entrySet().removeIf(entry -> entry.getValue().getValue() == null);
            log.info("Read and merge cache file success.");
        } catch (CommonException exception) {
            log.error("Read cache file failed.", exception);
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
        try (AutoCloseableLock ignored = new AutoCloseableLock(targetFileAndCacheLock)) {
            long now = System.currentTimeMillis();
            cache.put(key, new RecordValue<>(now, value));
            lastRefreshTime = now;
        }
        writeToTargetFile();
    }

    /**
     * 从缓存中移除
     *
     * @param key 缓存Key
     * @throws CommonException 抛出服务异常
     */
    public void remove(K key) throws CommonException {
        try (AutoCloseableLock ignored = new AutoCloseableLock(targetFileAndCacheLock)) {
            long now = System.currentTimeMillis();
            cache.put(key, new RecordValue<>(now, null)); // 置为null，将在写文件时删除
            lastRefreshTime = now;
        }
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
        try (AutoCloseableLock ignored = new AutoCloseableLock(targetFileAndCacheLock)) {
            cache.entrySet().removeIf(entry -> entry.getValue().getValue() == null);
            JsonUtil.toJson(cache, getTargetFile(targetFilePath));
            log.info("Write cache file success.");
        } catch (CommonException exception) {
            log.error("Write cache file failed.", exception);
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
    synchronized private static File getTargetFile(String targetFilePath) throws CommonException {
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
            Files.write(targetFilePathObj, "{}".getBytes(StandardCharsets.UTF_8));
            log.info("Target file \"{}\" created", targetFilePath);
            return targetFile;
        } catch (IOException exception) {
            log.error("Create target file \"{}\" failed.", targetFilePath);
            CommonException commonException = new CommonException(I18nConstant.RESOURCE_CACHE_FILE_CREATE_FAILED);
            commonException.setStackTrace(exception.getStackTrace());
            throw commonException;
        }
    }
}
