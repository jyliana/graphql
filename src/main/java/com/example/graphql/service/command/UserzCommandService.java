package com.example.graphql.service.command;

import com.example.graphql.datasource.problemz.entity.UserzToken;
import com.example.graphql.datasource.problemz.repository.UserzRepository;
import com.example.graphql.datasource.problemz.repository.UserzTokenRepository;
import com.example.graphql.exception.ProblemAuthenticationException;
import com.example.graphql.util.HashUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserzCommandService {

  private UserzRepository userzRepository;
  private UserzTokenRepository userzTokenRepository;

  public UserzToken login(String username, String password) {
    var userzResult = userzRepository.findByUsernameIgnoreCase(username);

    if (userzResult.isEmpty() ||
            !HashUtil.isBcryptMatch(password, userzResult.get().getHashedPassword())) {
      throw new ProblemAuthenticationException();
    }

    var randomAuthToken = RandomStringUtils.randomAlphanumeric(40);
    return refreshToken(userzResult.get().getId(), randomAuthToken);
  }

  private UserzToken refreshToken(UUID userId, String authToken) {
    var now = LocalDateTime.now();

    var userzToken = UserzToken.builder()
            .userId(userId)
            .authToken(authToken)
            .creationTimestamp(now)
            .expiryTimestamp(now.plusHours(2))
            .build();

    return userzTokenRepository.save(userzToken);
  }

}
