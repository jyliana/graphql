package com.example.graphql.service.query;

import com.example.graphql.datasource.problemz.entity.Userz;
import com.example.graphql.datasource.problemz.repository.UserzRepository;
import com.example.graphql.exception.ProblemAuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserzQueryService {

  private UserzRepository userzRepository;

  public Userz findUserzByAuthToken(String authToken) {
    return userzRepository.findUserByToken(authToken).orElseThrow(ProblemAuthenticationException::new);
  }
}
