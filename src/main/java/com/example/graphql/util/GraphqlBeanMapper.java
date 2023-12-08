package com.example.graphql.util;

import com.example.graphql.datasource.problemz.entity.Problemz;
import com.example.graphql.datasource.problemz.entity.Solutionz;
import com.example.graphql.datasource.problemz.entity.Userz;
import com.example.graphql.datasource.problemz.entity.UserzToken;
import com.example.graphql.types.Problem;
import com.example.graphql.types.Solution;
import com.example.graphql.types.User;
import com.example.graphql.types.UserAuthToken;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class GraphqlBeanMapper {

  private static final PrettyTime PRETTY_TIME = new PrettyTime();
  private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(2);


  abstract User mapToGraphql(Userz user);

  abstract Problem mapToGraphql(Problemz original);

  abstract Solution mapToGraphql(Solutionz original);

  abstract UserAuthToken mapToGraphql(UserzToken original);

}
