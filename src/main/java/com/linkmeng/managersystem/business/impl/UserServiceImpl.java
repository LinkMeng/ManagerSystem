package com.linkmeng.managersystem.business.impl;

import com.linkmeng.managersystem.business.UserService;
import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.dao.UserResourceDao;
import com.linkmeng.managersystem.model.CommonMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @since 2024-08-16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserResourceDao userResourceDao;

    @Override
    public CommonMessage checkAccount(Integer userId, String resource) throws CommonException {
        boolean permission = userResourceDao.queryResourcesByUserId(userId).contains(resource);
        String i18nKey = permission
            ? I18nConstant.MESSAGE_USER_CHECK_ACCOUNT_SUCCEEDED : I18nConstant.MESSAGE_USER_CHECK_ACCOUNT_FAILED;
        return CommonMessage.of(permission, i18nKey, userId, resource);
    }
}
