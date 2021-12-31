package com.hooon.base.domain.account.dto;

import com.hooon.base.domain.account.entity.Account;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDto {
  @NotNull
  private String name;
  @NotNull
  private String email;

  private AccountDto(final String name, final String email) {
    this.name = name;
    this.email = email;
  }

  public static AccountDto of(final Account account) {
    return new AccountDto(account.getName(), account.getEmail());
  }
}
