package com.hooon.base.global.security.model;

import lombok.Generated;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Transient;
import java.util.Collection;
import java.util.Objects;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  @Transient
  private final Object principal;

  @Transient
  private Object credentials;

  public JwtAuthenticationToken(final Object principal, final Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(false);
  }

  public JwtAuthenticationToken(
      final Object principal,
      final Object credentials,
      final Collection<? extends GrantedAuthority> authorities
  ) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    credentials = null;
  }

  @Override
  public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }
    super.setAuthenticated(false);
  }

  @Generated
  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof JwtAuthenticationToken)) {
      return false;
    }

    if (!super.equals(object)) {
      return false;
    }
    final JwtAuthenticationToken token = (JwtAuthenticationToken) object;
    return Objects.equals(principal, token.principal);
  }

  @Generated
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), principal);
  }
}
