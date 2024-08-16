package com.linkmeng.managersystem.common.exception;

import com.linkmeng.managersystem.common.exception.handler.HttpStatusException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义公共异常
 *
 * @since 2024-08-16
 */
@Getter
public class CommonException extends Exception implements HttpStatusException {
  private final String exceptionId;
  private final Object[] arguments;

  /**
   * 构造方法
   *
   * @param id 国际化ID
   * @param args 国际化参数
   */
  public CommonException(String id, Object... args) {
    super(id);
    this.exceptionId = id;
    this.arguments = args;
  }

  /**
   * 构造方法
   *
   * @param id 国际化ID
   * @param message 错误信息
   * @param args 国际化参数
   */
  public CommonException(String id, String message, Object... args) {
    super(message);
    this.exceptionId = id;
    this.arguments = args;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
