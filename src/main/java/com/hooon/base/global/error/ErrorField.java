package com.hooon.base.global.error;

import org.springframework.validation.FieldError;

import java.util.Objects;

public class ErrorField {
  private String field;
  private String value;
  private String reason;

  private ErrorField(final FieldError fieldError) {
    this.field = fieldError.getField();
    this.value = Objects.requireNonNull(fieldError.getRejectedValue()).toString();
    this.reason = fieldError.getDefaultMessage();
  }

  public static ErrorField mapper(final FieldError fieldError) {
    return new ErrorField(fieldError);
  }
}
