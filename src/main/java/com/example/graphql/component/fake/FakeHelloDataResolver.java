package com.example.graphql.component.fake;

import com.example.graphql.types.Hello;
import com.example.graphql.datasource.fake.FakeHelloDataSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@DgsComponent
@AllArgsConstructor
public class FakeHelloDataResolver {

  FakeHelloDataSource fakeHelloDataSource;

  @DgsQuery
  public List<Hello> allHellos() {
    return fakeHelloDataSource.getHelloList();
  }

  @DgsQuery
  public Hello oneHello() {
    var helloList = fakeHelloDataSource.getHelloList();
    return helloList.get(
            ThreadLocalRandom.current().nextInt(helloList.size())
    );
  }
}
