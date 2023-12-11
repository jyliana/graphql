package com.example.graphql.component.problemz;

import com.example.graphql.DgsConstants;
import com.example.graphql.types.Solution;
import com.example.graphql.types.SolutionCreateInput;
import com.example.graphql.types.SolutionResponse;
import com.example.graphql.types.SolutionVoteInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class SolutionDataResolver {

  @DgsMutation(field = DgsConstants.MUTATION.SolutionCreate)
  public SolutionResponse createSolution(
          @RequestHeader(name = "authToken") String authToken,
          @InputArgument(name = "newSolution") SolutionCreateInput solutionCreateInput) {
    return null;
  }

  @DgsMutation(field = DgsConstants.MUTATION.SolutionVote)
  public SolutionResponse createSolutionVote(
          @RequestHeader(name = "authToken") String authToken,
          @InputArgument(name = "newVote") SolutionVoteInput solutionVoteInput) {
    return null;
  }

  @DgsSubscription(field = DgsConstants.SUBSCRIPTION.SolutionVoteChanged)
  public Flux<Solution> subscribeSolutionVote(@InputArgument(name = "solutionId") String solutionId) {
    return null;
  }
}
