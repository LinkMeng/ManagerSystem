package com.linkmeng.managersystem.dao.impl;

import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.dao.UserResourceDao;
import com.linkmeng.managersystem.common.cache.ResourceFileCache;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Collections;
import java.util.Set;

/**
 * 用户资源关系查询
 *
 * @since 2024-08-16
 */
@Repository
public class UserResourceDaoImpl implements UserResourceDao {
    private static final String RESOURCE_FILE_NAME = "user_resource_data.json";

    /**
     * 用户资源关系缓存
     */
    private static ResourceFileCache<Integer, Set<String>> userResourceMapper;

    @Override
    public int insertUserResource(Integer userId, Set<String> resources) throws CommonException {
        ResourceFileCache<Integer, Set<String>> mapper = getMapper();
        if (CollectionUtils.isEmpty(resources)) {
            mapper.remove(userId);
            return 0;
        }
        mapper.put(userId, resources);
        return resources.size();
    }

    @Override
    public Set<String> queryResourcesByUserId(Integer userId) throws CommonException {
        ResourceFileCache<Integer, Set<String>> mapper = getMapper();
        Set<String> resources = mapper.get(userId);
        return CollectionUtils.isEmpty(resources) ? Collections.emptySet() : resources;
    }

    /**
     * 获取用户资源关系缓存实例
     *
     * @return 缓存实例
     * @throws CommonException 抛出服务异常
     */
    private ResourceFileCache<Integer, Set<String>> getMapper() throws CommonException {
        if (userResourceMapper == null) {
            userResourceMapper = ResourceFileCache.of(String.join(File.separator, ".", RESOURCE_FILE_NAME));
        }
        return userResourceMapper;
    }
}
