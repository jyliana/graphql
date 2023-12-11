package com.example.graphql.component.problemz;

import com.example.graphql.DgsConstants;
import com.example.graphql.service.query.ProblemzQueryService;
import com.example.graphql.types.Problem;
import com.example.graphql.types.ProblemCreateInput;
import com.example.graphql.types.ProblemResponse;
import com.example.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static com.example.graphql.util.GraphqlBeanMapper.mapToGraphql;

@DgsComponent
@AllArgsConstructor
public class ProblemDataResolver {

  private ProblemzQueryService queryService;

  @DgsQuery(field = DgsConstants.QUERY.ProblemLatestList)
  public List<Problem> getProblemLatestList() {
    return queryService.problemzLatestList().stream().map(GraphqlBeanMapper::mapToGraphql).toList();
  }

  @DgsQuery(field = DgsConstants.QUERY.ProblemDetail)
  public Problem getProblemDetail(@InputArgument(name = "id") String problemId) throws Exception {
    var problemz = queryService.problemzDetail(UUID.fromString(problemId));
    return mapToGraphql(problemz);
  }

  @DgsMutation(field = DgsConstants.MUTATION.ProblemCreate)
  public ProblemResponse createProblem(@RequestHeader(name = "authToken", required = true) String authToken,
                                       @InputArgument(name = "problem") ProblemCreateInput problemCreateInput) {
    return null;
  }

  @DgsSubscription(field = DgsConstants.SUBSCRIPTION.ProblemAdded)
  public Flux<Problem> subscribeProblemAdded() {
    return null;
  }
}
