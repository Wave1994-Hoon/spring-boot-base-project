package com.hooon.base.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  private int statusCode;
  private String message;
  private List<ErrorField> errors;
  private LocalDateTime timestamp = LocalDateTime.now();

  private ErrorResponse(final int statusCode, final String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  @Builder
  public ErrorResponse(
      final int statusCode, final String message, final List<ErrorField> errors) {
    this.statusCode = statusCode;
    this.message = message;
    this.errors = errors;
  }

  public static ErrorResponse of(final int statusCode, final String message) {
    return new ErrorResponse(statusCode, message);
  }
}

