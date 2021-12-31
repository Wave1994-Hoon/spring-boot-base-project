package com.hooon.base.domain.account.service;

import com.hooon.base.domain.account.dto.AccountDto;
import com.hooon.base.domain.account.dto.SignUpDto;
import com.hooon.base.domain.account.entity.Account;
import com.hooon.base.domain.account.repository.AccountRepository;
import com.hooon.base.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public AccountDto createAccount(@Valid final SignUpDto accountDto) {
    final Account existAccount = accountRepository.findAccountByEmail(accountDto.getEmail()).orElse(null);
    if (existAccount != null) {
      throw new IllegalArgumentException("duplicated");
    }

    final Account account = Account.builder()
        .name(accountDto.getName())
        .email(accountDto.getEmail())
        .password(passwordEncoder.encode(accountDto.getPassword()))
        .build();

    return AccountDto.of(accountRepository.save(account));
  }

  @Transactional(readOnly = true)
  public AccountDto getAccountDtoByEmail(final String email) {
    final Account account = accountRepository.findAccountByEmail(email).orElseThrow(EntityNotFoundException::new);
    return AccountDto.of(account);
  }
}
