package com.hooon.base.global.error.exception;

import com.hooon.base.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{

  public EntityNotFoundException() {
    super(ErrorCode.ENTITY_NOT_FOUND);
  }

  public EntityNotFoundException(final String message) {
    super(message, ErrorCode.ENTITY_NOT_FOUND);
  }

  public EntityNotFoundException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
