package com.linkmeng.managersystem.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 国际化ID常亮类
 *
 * @since 2024-08-16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class I18nConstant {
    public static final String MESSAGE_ADMIN_ADD_USER_SUCCESS = "message.admin.addUser.success";

    public static final String MESSAGE_USER_CHECK_ACCOUNT_SUCCEEDED = "message.user.checkAccount.succeeded";
    public static final String MESSAGE_USER_CHECK_ACCOUNT_FAILED = "message.user.checkAccount.failed";

    public static final String COMMON_ROLE_USER_NOT_FOUND = "common.role.userNotFound";
    public static final String COMMON_ROLE_USER_PERMISSION_DENIED = "common.role.userPermissionDenied";

    public static final String COMMON_SERIALIZE_USER_PARSE_FAILED = "common.serialize.userParseFailed";

    public static final String COMMON_INPUT_PARAM_CHECK_FAILED = "common.input.paramCheckFailed";

    public static final String RESOURCE_CACHE_FILE_PATH_ILLEGAL = "resource.cache.filePathIllegal";
    public static final String RESOURCE_CACHE_FILE_CREATE_FAILED = "resource.cache.fileCreateFailed";
}
