package com.cjdx.supermarket.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.Date;

@Component
public class JwtUtil {

  @Value("${jwt.secret:defaultSecretKey}")
  private String secret;

  @Value("${jwt.expiration:86400000}")
  private long expiration;

  @PostConstruct
  public void init() {
    // 确保secret不为空
    if (secret == null || secret.isEmpty()) {
      secret = "defaultSecretKey";
    }
  }

  private Algorithm getAlgorithm() {
    return Algorithm.HMAC256(secret);
  }

  /**
   * 生成JWT token
   */
  public String generateToken(Long userId, String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);

    return JWT.create()
        .withSubject(String.valueOf(userId))
        .withClaim("username", username)
        .withIssuedAt(now)
        .withExpiresAt(expiryDate)
        .sign(getAlgorithm());
  }

  /**
   * 验证JWT token
   */
  public boolean validateToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(getAlgorithm()).build();
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  /**
   * 从token中获取用户ID
   */
  public Long getUserIdFromToken(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return Long.parseLong(jwt.getSubject());
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 从token中获取用户名
   */
  public String getUsernameFromToken(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("username").asString();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 解析JWT token
   */
  public DecodedJWT decodeToken(String token) {
    try {
      return JWT.decode(token);
    } catch (Exception e) {
      return null;
    }
  }
}