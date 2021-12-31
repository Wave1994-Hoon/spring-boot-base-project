package com.hooon.base.domain.account.repository;

import com.hooon.base.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findAccountByEmail(final String email);

}
