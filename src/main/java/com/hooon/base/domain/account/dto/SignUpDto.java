package com.hooon.base.domain.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpDto {
  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @Builder
  public SignUpDto(final String name, final String email, final String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }
}
