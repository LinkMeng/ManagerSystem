package com.linkmeng.managersystem.common.exception;

import lombok.Getter;

/**
 * 自定义公共异常
 *
 * @since 2024-08-16
 */
@Getter
public class CommonException extends Exception {
  private final String exceptionId;

  /**
   * 构造方法
   *
   * @param id 国际化ID
   * @param message 错误信息
   */
  public CommonException(String id, String message) {
    super(message);
    this.exceptionId = id;
  }
}
