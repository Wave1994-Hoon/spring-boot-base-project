package com.hooon.base.domain.account.controller;

import com.hooon.base.domain.account.dto.AccountDto;
import com.hooon.base.domain.account.dto.SignUpDto;
import com.hooon.base.domain.account.service.AccountService;
import com.hooon.base.global.security.model.UserDetailsContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
  private final AccountService accountService;

  @PostMapping("/sign-up")
  public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid final SignUpDto signUpDto) {
    return ResponseEntity.ok(accountService.createAccount(signUpDto));
  }

  @GetMapping("")
  public ResponseEntity<AccountDto> getAccount(@AuthenticationPrincipal final UserDetailsContext userDetailsContext) {
    return ResponseEntity.ok(accountService.getAccountDtoByEmail(userDetailsContext.getEmail()));
  }

}
