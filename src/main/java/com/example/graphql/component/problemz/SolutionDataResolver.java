package com.example.graphql.component.problemz;

import com.example.graphql.DgsConstants;
import com.example.graphql.datasource.problemz.entity.Solutionz;
import com.example.graphql.service.command.SolutionzCommandService;
import com.example.graphql.service.query.ProblemzQueryService;
import com.example.graphql.service.query.UserzQueryService;
import com.example.graphql.types.Solution;
import com.example.graphql.types.SolutionCreateInput;
import com.example.graphql.types.SolutionResponse;
import com.example.graphql.types.SolutionVoteInput;
import com.example.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.UUID;

import static com.example.graphql.util.GraphqlBeanMapper.mapToEntity;
import static com.example.graphql.util.GraphqlBeanMapper.mapToGraphql;

@DgsComponent
@AllArgsConstructor
public class SolutionDataResolver {

  private UserzQueryService userzQueryService;
  private ProblemzQueryService problemzQueryService;
  private SolutionzCommandService solutionzCommandService;

  @DgsMutation(field = DgsConstants.MUTATION.SolutionCreate)
  public SolutionResponse createSolution(
          @RequestHeader(name = "authToken") String authToken,
          @InputArgument(name = "solution") SolutionCreateInput solutionCreateInput) {
    var userz = userzQueryService.findUserzByAuthToken(authToken);
    var problemzId = UUID.fromString(solutionCreateInput.getProblemId());
    var problemz = problemzQueryService.problemzDetail(problemzId);
    var solutionz = mapToEntity(solutionCreateInput, userz, problemz);
    var created = solutionzCommandService.createSolution(solutionz);

    return SolutionResponse.newBuilder()
            .solution(mapToGraphql(created))
            .build();
  }

  @DgsMutation(field = DgsConstants.MUTATION.SolutionVote)
  public SolutionResponse createSolutionVote(
          @RequestHeader(name = "authToken") String authToken,
          @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput) {
    Optional<Solutionz> updated;
    userzQueryService.findUserzByAuthToken(authToken);
    if (solutionVoteInput.getVoteAsGood()) {
      updated = solutionzCommandService.voteGood(UUID.fromString(solutionVoteInput.getSolutionId()));
    } else updated = solutionzCommandService.voteBad(UUID.fromString(solutionVoteInput.getSolutionId()));
    if (updated.isEmpty()) {
      throw new DgsEntityNotFoundException("Solution " + solutionVoteInput.getSolutionId() + " not found");
    }

    return SolutionResponse.newBuilder().solution(mapToGraphql(updated.get())).build();
  }

  @DgsSubscription(field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged)
  public Flux<Solution> subscribeSolutionVote(@InputArgument(name = "solutionId") String solutionId) {
    return solutionzCommandService.solutionzFlux().map(GraphqlBeanMapper::mapToGraphql);
  }
}
