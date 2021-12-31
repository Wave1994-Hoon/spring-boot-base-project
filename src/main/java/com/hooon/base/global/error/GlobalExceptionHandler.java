package com.hooon.base.global.error;

import com.hooon.base.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * GlobalExceptionHandler 에 등록된 메서드 이외 모든 예외 처리를 담당한다.
   * example) Null Point Exception 등
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus().value(), e.getMessage());
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

  /**
   * 비즈니스 로직에서 발생하는 모든 예외 처리를 담당한다.
   * 즉, 도메인 로직에서 발생 하는 예외는 해당 메소드를 통해 에러를 핸들링 한다.
   */
  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> businessError(BusinessException e) {
    final ErrorCode errorCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage());
    return ResponseEntity.status(errorCode.getStatus()).body(response);
  }


  /**
   * javax.validation.Valid or @Validated 으로 binding error 발생하는 예외를 담당한다.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
    final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage());
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

  /**
   * type 일치하지 않아 binding 하지 못 할 경우 발생하는 예외를 담당한다.
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    final ErrorCode errorCode = ErrorCode.INVALID_TYPE_VALUE;
    final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage());
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

  /**
   * 지원하지 않은 HTTP method 호출 할 경우 발생하는 예외를 담당한다.
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    final ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
    final ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage());
    return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
  }

}
