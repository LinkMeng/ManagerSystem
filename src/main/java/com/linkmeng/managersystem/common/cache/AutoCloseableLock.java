package com.linkmeng.managersystem.common.cache;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可自动关闭的可重入锁
 *
 * @since 2024-08-17
 */
public class AutoCloseableLock implements AutoCloseable {
    private final ReentrantLock lock;

    /**
     * 构造方法
     *
     * @param lock 可重入锁
     */
    public AutoCloseableLock(ReentrantLock lock) {
        this.lock = lock;
        this.lock.lock();
    }

    @Override
    public void close() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
