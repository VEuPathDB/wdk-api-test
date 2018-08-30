package org.gusdb.wdk.model.api;

public class LoginRequest {
  private final String email;

  private final String password;

  private final String redirectUrl;

  public LoginRequest(String email, String password, String redirectUrl) {
    this.email = email;
    this.password = password;
    this.redirectUrl = redirectUrl;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }
}
