package com.example.graphql.component.problemz;

import com.example.graphql.DgsConstants;
import com.example.graphql.service.command.UserzCommandService;
import com.example.graphql.service.query.UserzQueryService;
import com.example.graphql.types.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.example.graphql.util.GraphqlBeanMapper.mapToEntity;
import static com.example.graphql.util.GraphqlBeanMapper.mapToGraphql;

@DgsComponent
@AllArgsConstructor
public class UserDataResolver {

  private UserzQueryService userzQueryService;
  private UserzCommandService userzCommandService;

  @DgsQuery(field = DgsConstants.QUERY.Me)
  public User accountInfo(@RequestHeader(name = "authToken") String authToken) {
    var userzByAuthToken = userzQueryService.findUserzByAuthToken(authToken);
    return mapToGraphql(userzByAuthToken);
  }

  @DgsMutation(field = DgsConstants.MUTATION.UserCreate)
  public UserResponse createUser(@InputArgument(name = "user") UserCreateInput userCreateInput) {
    var userz = mapToEntity(userCreateInput);
    var saved = userzCommandService.createUserz(userz);
    return UserResponse.newBuilder().user(mapToGraphql(saved)).build();
  }

  @DgsMutation
  public UserResponse userLogin(@InputArgument(name = "user") UserLoginInput userLoginInput) {
    var generatedToken = userzCommandService.login(userLoginInput.getUsername(), userLoginInput.getPassword());
    var userAuthToken = mapToGraphql(generatedToken);
    var userInfo = accountInfo(userAuthToken.getAuthToken());

    return UserResponse.newBuilder()
            .authToken(userAuthToken)
            .user(userInfo)
            .build();
  }

  @DgsMutation
  public UserActivationResponse userActivation(@InputArgument(name = "user") UserActivationInput input) {
    var updated = userzCommandService.activateUserz(input.getUsername(), input.getActive())
            .orElseThrow(DgsEntityNotFoundException::new);

    return UserActivationResponse.newBuilder().isActive(updated.isActive()).build();
  }
}
