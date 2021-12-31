package com.hooon.base.global.security.factory;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtFactory {
  private final Key key;

  public JwtFactory(@Value(value = "${jwt.secret}") final String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateToken(final String email, final int expiredTime) {
    HashMap<String, Object> claims = new HashMap<>();
    return createToken(claims, email, expiredTime);
  }

  public String extractEmail(final String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public boolean isValidToken(final String token) {
    try {
      extractAllClaims(token);
      return true;
    } catch (JwtException exception) {
      log.error("JWT Exception");
    }
    return false;
  }

  private String createToken(final HashMap<String, Object> claims, final String email, final int expiredTime) {
    return Jwts.builder()
        .setHeader(settingHeaders())
        .signWith(key, SignatureAlgorithm.HS512)
        .setClaims(claims)
        .setSubject(email)
        .setIssuedAt(settingsDate(0))
        .setExpiration(settingsDate(expiredTime))
        .compact();
  }

  private Date settingsDate(final int plusTime) {
    return Date.from(LocalDateTime.now().plusMinutes(plusTime)
        .atZone(ZoneId.systemDefault())
        .toInstant()
    );
  }

  private Map<String, Object> settingHeaders() {
    final HashMap<String, Object> headers = new HashMap<>();
    headers.put("type", Header.JWT_TYPE);
    headers.put("algorithm", SignatureAlgorithm.HS512);
    return  headers;
  }

  private <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
  }
}

