package com.example.graphql.component.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@DgsComponent
public class FakeAdditionalOnRequestDataResolver {

  @DgsQuery
  public String additionalOnRequest(
          @RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
          @RequestHeader(name = "mandatoryHeader") String mandatoryHeader,
          @RequestParam(name = "optionalParam", required = false) String optionalParam,
          @RequestParam(name = "mandatoryParam") String mandatoryParam
  ) {
    var sb = new StringBuilder();
    sb.append("Optional header : ").append(optionalHeader).append(", \n");
    sb.append("Mandatory header : ").append(mandatoryHeader).append(", \n");
    sb.append("Optional param : ").append(optionalParam).append(", \n");
    sb.append("Mandatory param : ").append(mandatoryParam);

    return sb.toString();
  }
}
