package com.example.graphql.service.query;

import com.example.graphql.datasource.problemz.entity.Solutionz;
import com.example.graphql.datasource.problemz.repository.SolutionzRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolutionzQueryService {
  private SolutionzRepository repository;

  public List<Solutionz> solutionzByKeyword(String keyword) {
    return repository.findByKeyword("%" + keyword + "%");
  }
}
