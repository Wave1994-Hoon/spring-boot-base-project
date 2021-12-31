package com.hooon.base.domain.account.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {
  @NotNull
  private String email;
  @NotNull
  private String password;

  private LoginDto(final String email, final String password) {
    this.email = email;
    this.password = password;
  }

  public static LoginDto of(final String email, final String password) {
    return new LoginDto(email, password);
  }
}
