package com.hooon.base.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooon.base.global.security.factory.JwtFactory;
import com.hooon.base.global.security.filter.JwtAuthenticationFilter;
import com.hooon.base.global.security.filter.JwtAuthorizationFilter;
import com.hooon.base.global.security.provider.JwtAuthenticationProvider;
import com.hooon.base.global.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsServiceImpl userDetailsService;
  private final ObjectMapper objectMapper;
  private final JwtFactory jwtFactory;

  protected static final String[] PUBLIC_URIS = {
      "/", "/v3/api-docs", "/webjars/**", "/swagger-resources/**", "/swagger-ui/**", "/javainuse-openapi/**",
      "/error", "/favicon.ico"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }


  @Bean
  public AuthenticationProvider authenticationProvider() {
    return new JwtAuthenticationProvider(userDetailsService, passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .formLogin().disable()
        .logout().disable()
        .headers().frameOptions().disable()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http
        .authorizeRequests()
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        .antMatchers(PUBLIC_URIS).permitAll()
        .antMatchers(POST, "/api/v1/login").permitAll()
        .antMatchers(POST,"/api/v1/account/sign-up").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(jwtAuthenticationFilter())
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, jwtFactory));

    http
        .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
    });
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    source.registerCorsConfiguration("/api/**", config);
    return new CorsFilter(source);
  }

  private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    final JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtFactory, objectMapper);
    jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
    return jwtAuthenticationFilter;
  }
}