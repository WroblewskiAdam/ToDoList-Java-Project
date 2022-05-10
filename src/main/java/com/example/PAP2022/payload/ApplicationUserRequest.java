package com.example.PAP2022.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationUserRequest {
  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private MultipartFile image;
}
