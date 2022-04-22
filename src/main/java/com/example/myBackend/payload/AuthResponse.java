package com.example.myBackend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String email;
  private String role;

  public AuthResponse(String accessToken, Long id, String email, String role) {
    this.id = id;
    this.role = role;
    this.email = email;
    this.token = accessToken;
  }
}
