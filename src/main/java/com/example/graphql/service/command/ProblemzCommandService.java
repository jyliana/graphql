package com.example.graphql.service.command;

import com.example.graphql.datasource.problemz.entity.Problemz;
import com.example.graphql.datasource.problemz.repository.ProblemzRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service

public class ProblemzCommandService {

  private Sinks.Many<Problemz> problemzSink = Sinks.many().multicast().onBackpressureBuffer();
  private ProblemzRepository repository;

  public ProblemzCommandService(ProblemzRepository repository) {
    this.repository = repository;
  }

  public Problemz createProblem(Problemz problem) {
    var created = repository.save(problem);
    problemzSink.tryEmitNext(created);
    return created;
  }

  public Flux<Problemz> problemzFlux(){
    return problemzSink.asFlux();
  }
}
