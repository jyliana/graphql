package com.example.graphql.service.command;

import com.example.graphql.datasource.problemz.entity.Problemz;
import com.example.graphql.datasource.problemz.repository.ProblemzRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProblemzCommandService {

  private ProblemzRepository repository;

  public Problemz createProblem(Problemz problem){
    return repository.save(problem);
  }
}
