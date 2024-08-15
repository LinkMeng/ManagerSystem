package com.linkmeng.managersystem.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.linkmeng.managersystem.common.Base64Util;
import com.linkmeng.managersystem.common.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 用户模型类
 *
 * @since 2024-08-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private int userId;

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
     */
    public static User ofBase64(String base64) {
        return JsonUtil.fromJson(Base64Util.decode(base64), User.class); // TODO: 校验
    }
}
