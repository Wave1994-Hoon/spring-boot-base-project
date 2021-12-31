package com.hooon.base.global.security.model;

import com.hooon.base.domain.account.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsContext implements UserDetails {

  private final String email;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public UserDetailsContext(final Account account, final Collection<? extends GrantedAuthority> authorities) {
    this.email = account.getEmail();
    this.password = account.getPassword();
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Deprecated
  @Override
  public String getUsername() {
    return email;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}