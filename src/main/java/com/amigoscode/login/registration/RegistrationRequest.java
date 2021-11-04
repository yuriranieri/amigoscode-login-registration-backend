package com.amigoscode.login.registration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationRequest {

  @NotBlank(message = "first name is required")
  private final String firstName;

  @NotBlank(message = "last name is required")
  private final String lastName;

  @Email(message = "email not valid")
  private final String email;

  @NotBlank(message = "password is required")
  private final String password;

}
