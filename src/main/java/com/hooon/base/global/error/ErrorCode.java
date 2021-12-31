package com.hooon.base.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  // global
  ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Entity Not Found"),
  INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value"),
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid Input Value"),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error")
  ;

  // account

  private final HttpStatus status;
  private final String message;

  ErrorCode(final HttpStatus status, final String message) {
    this.status = status;
    this.message = message;
  }
}
