package com.linkmeng.managersystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 用户资源模型
 *
 * @since 2024-08-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResource {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 资源ID列表
     */
    private Set<String> endpoint;
}
