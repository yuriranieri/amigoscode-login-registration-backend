package com.amigoscode.login.registration;

import com.amigoscode.login.appuser.AppUser;
import com.amigoscode.login.registration.token.ConfirmationToken;
import com.amigoscode.login.registration.token.ConfirmationTokenService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

  private final RegistrationService registrationService;
  private final ConfirmationTokenService tokenService;

  @PostMapping
  public String register(@RequestBody @Valid RegistrationRequest request) {
    return registrationService.register(request);
  }

  @GetMapping("/confirm")
  public String confirm(@RequestParam("token") String token) {
    return registrationService.confirmToken(token);
  }

  @GetMapping
  public List<AppUser> findAllUsers() {
    return registrationService.findAllUsers();
  }

  @GetMapping("/token")
  public List<ConfirmationToken> getConfirmationTokens() {
    return tokenService.getConfirmationTokens();
  }

}
