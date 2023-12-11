package com.example.graphql.service.query;

import com.example.graphql.datasource.problemz.entity.Userz;
import com.example.graphql.datasource.problemz.repository.UserzRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserzQueryService {

  private UserzRepository userzRepository;

  public Optional<Userz> findUserzByAuthToken(String authToken) {
    return userzRepository.findUserByToken(authToken);
  }
}
