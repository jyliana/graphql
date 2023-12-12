package com.example.graphql.service.command;

import com.example.graphql.datasource.problemz.entity.Solutionz;
import com.example.graphql.datasource.problemz.repository.SolutionzRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;
import java.util.UUID;

@Service

public class SolutionzCommandService {

  private Sinks.Many<Solutionz> solutionzSink = Sinks.many().multicast().onBackpressureBuffer();
  private SolutionzRepository repository;

  public SolutionzCommandService(SolutionzRepository repository) {
    this.repository = repository;
  }

  public Solutionz createSolution(Solutionz solutionz) {
    return repository.save(solutionz);
  }

  public Optional<Solutionz> voteBad(UUID solutionId) {
    repository.addVoteBadCount(solutionId);
    return getSolutionz(solutionId);
  }

  public Optional<Solutionz> voteGood(UUID solutionId) {
    repository.addVoteGoodCount(solutionId);
    return getSolutionz(solutionId);
  }

  @NotNull
  private Optional<Solutionz> getSolutionz(UUID solutionId) {
    var updated = repository.findById(solutionId);
    updated.ifPresent(solutionz -> solutionzSink.tryEmitNext(solutionz));
    return updated;
  }

  public Flux<Solutionz> solutionzFlux() {
    return solutionzSink.asFlux();
  }
}
