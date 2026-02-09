package com.cjdx.supermarket.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordUtil {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  /**
   * 使用BCrypt加密密码
   */
  public String encodePassword(String rawPassword) {
    return bCryptPasswordEncoder.encode(rawPassword);
  }

  /**
   * 验证密码
   */
  public boolean matches(String rawPassword, String encodedPassword) {
    return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
  }

  /**
   * 兼容旧密码格式的验证方法
   * 旧密码格式：MD5(password + salt) 然后Base64编码
   */
  public boolean matchesLegacy(String rawPassword, String encodedPassword, String salt) {
    try {
      // 计算MD5
      String passwordWithSalt = rawPassword + salt;
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hashBytes = md.digest(passwordWithSalt.getBytes("UTF-8"));

      // Base64编码
      String calculatedHash = Base64.getEncoder().encodeToString(hashBytes);

      // 调试输出
      System.out.println("Password: " + rawPassword);
      System.out.println("Salt: " + salt);
      System.out.println("PasswordWithSalt: " + passwordWithSalt);
      System.out.println("CalculatedHash: " + calculatedHash);
      System.out.println("StoredHash: " + encodedPassword);
      System.out.println("Match: " + calculatedHash.equals(encodedPassword));

      return calculatedHash.equals(encodedPassword);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 验证数据库中的MD5密码格式
   * 数据库中的密码格式：MD5(password + salt) 十六进制字符串
   */
  public boolean matchesDatabaseMD5(String rawPassword, String encodedPassword, String salt) {
    try {
      // 计算MD5
      String passwordWithSalt = rawPassword + salt;
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hashBytes = md.digest(passwordWithSalt.getBytes("UTF-8"));

      // 转换为十六进制字符串
      StringBuilder hexString = new StringBuilder();
      for (byte b : hashBytes) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }

      String calculatedHash = hexString.toString();

      return calculatedHash.equals(encodedPassword);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 验证数据库中的特殊MD5密码格式
   * 数据库中的密码格式：MD5(password) + 在特定位置插入盐值
   */
  public boolean matchesCustomMD5(String rawPassword, String encodedPassword, String salt) {
    try {
      // 1. 计算原始密码的MD5
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hashBytes = md.digest(rawPassword.getBytes("UTF-8"));

      // 转换为十六进制字符串
      StringBuilder hexString = new StringBuilder();
      for (byte b : hashBytes) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }

      String md5Hash = hexString.toString();

      // 2. 将盐值插入到MD5哈希的第18个字符之后
      String firstPart = md5Hash.substring(0, 18);
      String secondPart = md5Hash.substring(18);
      String calculatedHash = firstPart + salt + secondPart;

      return calculatedHash.equalsIgnoreCase(encodedPassword);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 生成盐值
   */
  public String generateSalt() {
    return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  }
}