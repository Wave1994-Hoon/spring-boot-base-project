package com.hooon.base.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooon.base.domain.account.dto.LoginDto;
import com.hooon.base.global.security.factory.JwtFactory;
import com.hooon.base.global.security.model.JwtAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private static final String BEARER_PREFIX = "Bearer ";

  private final ObjectMapper mapper;
  private final JwtFactory jwtFactory;

  public JwtAuthenticationFilter(
      final AuthenticationManager authenticationManager,
      final JwtFactory jwtFactory,
      final ObjectMapper mapper
  ) {
    super(authenticationManager);
    this.jwtFactory = jwtFactory;
    this.mapper = mapper;
  }

  @Override
  public Authentication attemptAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response
  ) throws AuthenticationException {
    try {
      final LoginDto loginUser = mapper.readValue(request.getReader(), LoginDto.class);
      final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(loginUser.getEmail(), loginUser.getPassword());
      return super.getAuthenticationManager().authenticate(jwtAuthenticationToken);
    } catch (IOException e) {
      throw new AuthenticationServiceException("Fail to authenticate user");
    }
  }

  @Override
  protected void successfulAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain,
      final Authentication authResult
  ) throws IOException {
    final String principal = (String) authResult.getPrincipal();
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + jwtFactory.generateToken(principal, 1));
    response.getWriter();
  }
}
