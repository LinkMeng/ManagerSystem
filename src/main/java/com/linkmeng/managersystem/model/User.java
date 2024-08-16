package com.linkmeng.managersystem.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.linkmeng.managersystem.common.Base64Util;
import com.linkmeng.managersystem.common.JsonUtil;
import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.common.exception.InputIllegalException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户模型类
 *
 * @since 2024-08-15
 */
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class User {

    /**
     * 用户角色枚举类
     *
     * @since 2024-08-15
     */
    @AllArgsConstructor
    @Getter
    public enum Role {
        ADMIN("admin"),
        USER("user");

        /**
         * 角色名称
         */
        @JsonValue
        private final String roleName;
    }

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String accountName;

    /**
     * 用户角色
     */
    private Role role;

    /**
     * 将Base64字符串反序列化成用户模型
     *
     * @param base64 Base64字符串
     * @return 用户模型
     * @throws CommonException 抛出服务异常
     */
    public static User ofBase64(String base64) throws CommonException {
        try {
            User user = JsonUtil.fromJson(Base64Util.decode(base64), User.class);
            checkUserAndThrow(user);
            return user;
        } catch (IllegalArgumentException exception) {
            log.error("Parse user info failed", exception);
            CommonException commonException = new CommonException(I18nConstant.COMMON_SERIALIZE_USER_PARSE_FAILED);
            commonException.initCause(exception);
            throw commonException;
        }
    }

    /**
     * 校验用户信息并抛异常
     *
     * @param user 用户信息
     * @throws CommonException 抛出服务异常
     */
    private static void checkUserAndThrow(User user) throws CommonException {
        if (user == null) {
            throw new InputIllegalException(I18nConstant.COMMON_INPUT_PARAM_CHECK_FAILED, "user");
        } else if (user.getUserId() == null || user.getUserId() < 0) {
            throw new InputIllegalException(I18nConstant.COMMON_INPUT_PARAM_CHECK_FAILED, "user.userId");
        } else if (StringUtils.isEmpty(user.getAccountName())) {
            throw new InputIllegalException(I18nConstant.COMMON_INPUT_PARAM_CHECK_FAILED, "user.accountName");
        } else if (user.getRole() == null) {
            throw new InputIllegalException(I18nConstant.COMMON_INPUT_PARAM_CHECK_FAILED, "user.role");
        }
    }
}
