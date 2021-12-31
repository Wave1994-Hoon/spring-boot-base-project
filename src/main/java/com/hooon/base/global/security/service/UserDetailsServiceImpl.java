package com.hooon.base.global.security.service;

import com.hooon.base.domain.account.entity.Account;
import com.hooon.base.domain.account.repository.AccountRepository;
import com.hooon.base.global.security.model.UserDetailsContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Transactional
  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    final Account user = accountRepository.findAccountByEmail(email).orElseThrow(EntityNotFoundException::new);
    return new UserDetailsContext(user, new ArrayList<>());
  }
}

