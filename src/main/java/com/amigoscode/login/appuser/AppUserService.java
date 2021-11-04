package com.amigoscode.login.appuser;

import static java.lang.String.format;

import com.amigoscode.login.registration.token.ConfirmationToken;
import com.amigoscode.login.registration.token.ConfirmationTokenService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

  private final static String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
  private final AppUserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ConfirmationTokenService confirmationTokenService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(format(USER_NOT_FOUND_MESSAGE, email)));
  }

  public String signUpUser(AppUser user) {
    validateUserEmail(user);

    String encodePassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodePassword);
    userRepository.save(user);

    String token = saveConfirmationToken(user);

    // TODO send email

    return token;
  }

  private String saveConfirmationToken(AppUser user) {
    String token = UUID.randomUUID().toString();
    var confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15), user);
    confirmationTokenService.saveConfirmationToken(confirmationToken);
    return token;
  }

  private void validateUserEmail(AppUser user) {
    boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

    if (userExists) {
      throw new IllegalStateException("email already taken");
    }
  }

  public List<AppUser> findAllUsers() {
    return userRepository.findAll();
  }

  public int enableAppUser(String email) {
    return userRepository.enableAppUser(email);
  }
}
