package com.example.graphql.util;

import com.example.graphql.datasource.problemz.entity.Problemz;
import com.example.graphql.datasource.problemz.entity.Solutionz;
import com.example.graphql.datasource.problemz.entity.Userz;
import com.example.graphql.datasource.problemz.entity.UserzToken;
import com.example.graphql.types.*;
import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.ZoneOffset;
import java.util.List;

public class GraphqlBeanMapper {

  private static final PrettyTime PRETTY_TIME = new PrettyTime();
  private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(2);

  private GraphqlBeanMapper() {
  }

  public static User mapToGraphql(Userz original) {
    return User.newBuilder()
            .id(original.getId().toString())
            .displayName(original.getDisplayName())
            .email(original.getEmail())
            .username(original.getUsername())
            .avatar(original.getAvatar())
            .createDateTime(original.getCreationTimestamp().atOffset(ZONE_OFFSET))
            .build();
  }

  public static Problem mapToGraphql(Problemz original) {
    var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);
    var author = mapToGraphql(original.getCreatedBy());
    var solutions = original.getSolutions().stream()
//            .sorted(Comparator.comparing(Solutionz::getCreationTimestamp).reversed())
            .map(GraphqlBeanMapper::mapToGraphql)
            .toList();
    var tagList = List.of(original.getTags().split(","));

    return Problem.newBuilder()
            .id(original.getId().toString())
            .createDateTime(createDateTime)
            .author(author)
            .title(original.getTitle())
            .content(original.getContent())
            .solutionCount(solutions.size())
            .solutions(solutions)
            .tags(tagList)
            .prettyCreateDateTime(PRETTY_TIME.format(createDateTime))
            .build();
  }

  public static Solution mapToGraphql(Solutionz original) {
    var author = mapToGraphql(original.getCreatedBy());
    var category = StringUtils.equalsIgnoreCase(original.getCategory(), SolutionCategory.EXPLANATION.toString()) ?
            SolutionCategory.EXPLANATION
            : SolutionCategory.REFERENCE;
    var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);

    return Solution.newBuilder()
            .id(original.getId().toString())
            .createDateTime(createDateTime)
            .author(author)
            .category(category)
            .content(original.getContent())
            .voteAsGoodCount(original.getVoteGoodCount())
            .voteAsBadCount(original.getVoteBadCount())
            .prettyCreateDateTime(PRETTY_TIME.format(createDateTime))
            .build();
  }

  public static UserAuthToken mapToGraphql(UserzToken original) {
    return UserAuthToken.newBuilder()
            .authToken(original.getAuthToken())
            .expiryTime(original.getExpiryTimestamp().atOffset(ZONE_OFFSET))
            .build();
  }

}
