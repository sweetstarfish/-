package com.cjdx.supermarket.entity;

public class LoginResponse {
  private String token;
  private User user;
  private String message;
  private boolean success;

  public LoginResponse() {
  }

  public LoginResponse(String token, User user, String message, boolean success) {
    this.token = token;
    this.user = user;
    this.message = message;
    this.success = success;
  }

  public static LoginResponse success(String token, User user) {
    return new LoginResponse(token, user, "登录成功", true);
  }

  public static LoginResponse error(String message) {
    return new LoginResponse(null, null, message, false);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    return "LoginResponse{" +
        "token='" + token + '\'' +
        ", user=" + user +
        ", message='" + message + '\'' +
        ", success=" + success +
        '}';
  }
}