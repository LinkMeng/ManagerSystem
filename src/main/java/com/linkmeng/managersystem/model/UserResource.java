package com.linkmeng.managersystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户资源模型
 *
 * @since 2024-08-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserResource {

    /**
     * 用户ID
     */
    private int userId;

    /**
     * 资源ID列表
     */
    private List<String> endpoint;
}
