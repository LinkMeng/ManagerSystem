package com.linkmeng.managersystem.business;

import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.model.CommonMessage;

/**
 * 用户服务接口
 *
 * @since 2024-08-16
 */
public interface UserService {
    /**
     * 校验用户的资源权限
     *
     * @param userId 用户ID
     * @param resource 资源ID
     * @return 操作结果
     * @throws CommonException 抛出服务异常
     */
    CommonMessage checkAccount(Integer userId, String resource) throws CommonException;
}
