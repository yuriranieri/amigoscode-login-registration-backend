package com.amigoscode.login.registration.token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

  private final ConfirmationTokenRepository tokenRepository;

  public void saveConfirmationToken(ConfirmationToken token) {
    tokenRepository.save(token);
  }

  public List<ConfirmationToken> getConfirmationTokens() {
    return tokenRepository.findAll();
  }

  public Optional<ConfirmationToken> getToken(String token) {
    return tokenRepository.findByToken(token);
  }

  public int setConfirmedAt(String token) {
    return tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
  }
}
