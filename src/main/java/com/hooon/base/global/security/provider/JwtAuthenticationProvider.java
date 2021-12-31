package com.hooon.base.global.security.provider;

import com.hooon.base.global.security.model.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
    final String principal = (String) authentication.getPrincipal();
    final String credentials = (String) authentication.getCredentials();
    final UserDetails userDetails = userDetailsService.loadUserByUsername(principal);

    if (!passwordEncoder.matches(credentials, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }

    return new JwtAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
