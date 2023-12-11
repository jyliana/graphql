package com.example.graphql.service.query;

import com.example.graphql.datasource.problemz.entity.Problemz;
import com.example.graphql.datasource.problemz.repository.ProblemzRepository;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProblemzQueryService {

  private ProblemzRepository repository;

  public List<Problemz> problemzLatestList() {
    return repository.findAllByOrderByCreationTimestampDesc();
  }

  public Problemz problemzDetail(UUID problemzId) {
    return repository.findById(problemzId).orElseThrow(DgsEntityNotFoundException::new);
  }

  public List<Problemz> problemzByKeyword(String keyword) {
    return repository.findByKeyword("%" + keyword + "%");
  }
}
