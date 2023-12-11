package com.example.graphql.component.problemz;

import com.example.graphql.DgsConstants;
import com.example.graphql.service.query.ProblemzQueryService;
import com.example.graphql.service.query.SolutionzQueryService;
import com.example.graphql.types.SearchItemFilter;
import com.example.graphql.types.SearchableItem;
import com.example.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@DgsComponent
@AllArgsConstructor
public class ItemSearchDataResolver {

  private ProblemzQueryService problemzQueryService;
  private SolutionzQueryService solutionzQueryService;

  @DgsQuery(field = DgsConstants.QUERY.ItemSearch)
  public List<SearchableItem> searchItems(@InputArgument(name = "filter") SearchItemFilter filter) {
    var keyword = filter.getKeyword();

    var problemList = problemzQueryService.problemzByKeyword(keyword).stream()
            .map(GraphqlBeanMapper::mapToGraphql)
            .toList();
    var solutionList = solutionzQueryService.solutionzByKeyword(keyword).stream()
            .map(GraphqlBeanMapper::mapToGraphql)
            .toList();

    var result = new ArrayList<SearchableItem>(problemList);
    result.addAll(solutionList);

    if (result.isEmpty()) {
      throw new DgsEntityNotFoundException("No item with keyword " + keyword);
    }
    result.sort(Comparator.comparing(SearchableItem::getCreateDateTime).reversed());

    return result;
  }
}
