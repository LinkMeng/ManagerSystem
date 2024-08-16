package com.linkmeng.managersystem.dao;

import com.linkmeng.managersystem.common.exception.CommonException;

import java.util.Set;

/**
 * 用户资源关系查询
 *
 * @since 2024-08-16
 */
public interface UserResourceDao {
    /**
     * 更新用户的资源权限
     *
     * @param userId 用户ID
     * @param resources 资源ID列表
     * @return 新的资源ID数量
     * @throws CommonException 抛出服务异常
     */
    int insertUserResource(Integer userId, Set<String> resources) throws CommonException;

    /**
     * 查询指定用户名下的资源
     *
     * @param userId 用户ID
     * @return 资源ID列表
     * @throws CommonException 抛出服务异常
     */
    Set<String> queryResourcesByUserId(Integer userId) throws CommonException;
}
