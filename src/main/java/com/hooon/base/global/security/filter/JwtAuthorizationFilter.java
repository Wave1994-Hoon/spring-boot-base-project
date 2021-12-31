package com.hooon.base.global.security.filter;

import com.hooon.base.global.security.factory.JwtFactory;
import com.hooon.base.global.security.model.JwtAuthenticationToken;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  private static final String BEARER_PREFIX = "Bearer ";
  private static final String REPLACEMENT_EMPTY_DELIMITER = "";

  private final UserDetailsService userDetailsService;
  private final JwtFactory jwtFactory;

  public JwtAuthorizationFilter(
      final AuthenticationManager authenticationManager,
      final UserDetailsService userDetailsService,
      final JwtFactory jwtFactory
  ) {
    super(authenticationManager);
    this.userDetailsService = userDetailsService;
    this.jwtFactory = jwtFactory;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain
  ) throws IOException, ServletException {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (Strings.isEmpty(header)) {
      chain.doFilter(request, response);
      return;
    }

    if (header.startsWith(BEARER_PREFIX)) {
      final String token = header.replace(BEARER_PREFIX, REPLACEMENT_EMPTY_DELIMITER);
      if (jwtFactory.isValidToken(token)) {
        final String email = jwtFactory.extractEmail(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (Objects.nonNull(userDetails)) {
          final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
          chain.doFilter(request, response);
        }
      }
    }
  }
}
