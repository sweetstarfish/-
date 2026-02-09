package com.cjdx.supermarket.controller;

import com.cjdx.supermarket.entity.LoginRequest;
import com.cjdx.supermarket.entity.LoginResponse;
import com.cjdx.supermarket.entity.User;
import com.cjdx.supermarket.service.UserService;
import com.cjdx.supermarket.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Resource
  private UserService userService;

  @Resource
  private JwtUtil jwtUtil;

  /**
   * 测试接口
   */
  @GetMapping("/test")
  public ResponseEntity<?> test() {
    return ResponseEntity.ok(Map.of("message", "Auth controller is working"));
  }

  /**
   * 用户登录
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      // 添加调试信息
      System.out.println("Login attempt - Username: " + loginRequest.getUsername());
      System.out.println("Login attempt - Password: " + loginRequest.getPassword());
      
      // 验证用户名和密码
      User user = userService.validateLogin(loginRequest.getUsername(), loginRequest.getPassword());

      if (user == null) {
        System.out.println("Login failed - User not found or password incorrect");
        return ResponseEntity.badRequest()
            .body(LoginResponse.error("用户名或密码错误"));
      }

      // 检查用户状态
      if (user.getStatus() != null && user.getStatus() != 1) {
        return ResponseEntity.badRequest()
            .body(LoginResponse.error("用户已被禁用"));
      }

      // 生成JWT token
      String token = jwtUtil.generateToken(user.getId(), user.getUserCode());

      // 清除敏感信息
      user.setUserPassword(null);
      user.setSalt(null);

      // 返回登录成功信息
      return ResponseEntity.ok(LoginResponse.success(token, user));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(LoginResponse.error("登录失败：" + e.getMessage()));
    }
  }

  /**
   * 用户退出
   */
  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
    try {
      // 这里可以添加token到黑名单的逻辑
      // 目前JWT是无状态的，客户端删除token即可

      Map<String, Object> response = new HashMap<>();
      response.put("message", "退出成功");
      response.put("success", true);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "退出失败", "success", false));
    }
  }

  /**
   * 获取当前用户信息
   */
  @GetMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
    try {
      // 移除Bearer前缀
      if (token.startsWith("Bearer ")) {
        token = token.substring(7);
      }

      // 验证token
      if (!jwtUtil.validateToken(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("message", "Token无效", "success", false));
      }

      // 获取用户ID
      Long userId = jwtUtil.getUserIdFromToken(token);
      if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("message", "Token解析失败", "success", false));
      }

      // 获取用户信息
      User user = userService.findById(userId);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "用户不存在", "success", false));
      }

      // 清除敏感信息
      user.setUserPassword(null);
      user.setSalt(null);

      Map<String, Object> response = new HashMap<>();
      response.put("user", user);
      response.put("success", true);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "获取用户信息失败", "success", false));
    }
  }

  /**
   * 验证token有效性
   */
  @GetMapping("/verify")
  public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String token) {
    try {
      // 移除Bearer前缀
      if (token.startsWith("Bearer ")) {
        token = token.substring(7);
      }

      boolean isValid = jwtUtil.validateToken(token);

      Map<String, Object> response = new HashMap<>();
      response.put("valid", isValid);
      response.put("success", true);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("valid", false, "success", false));
    }
  }
}