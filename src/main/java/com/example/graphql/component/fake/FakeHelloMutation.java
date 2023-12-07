package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeHelloDataSource;
import com.example.graphql.types.Hello;
import com.example.graphql.types.HelloInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DgsComponent
@AllArgsConstructor
public class FakeHelloMutation {

  private FakeHelloDataSource dataSource;

  @DgsMutation
  public int addHello(@InputArgument(name = "helloInput") HelloInput input) {
    var hello = Hello.newBuilder()
            .text(input.getText())
            .randomNumber(input.getNumber())
            .build();
    var newList = new ArrayList<>(dataSource.getHelloList());
    newList.add(hello);
    dataSource.setHelloList(newList);

    return dataSource.getHelloList().size();
  }

  @DgsMutation
  public List<Hello> replaceHelloText(@InputArgument(name = "helloInput") HelloInput input) {
    dataSource.getHelloList().forEach(hello -> {
      if (hello.getRandomNumber() == input.getNumber()) {
        hello.setText(input.getText());
      }
    });

    return dataSource.getHelloList();
  }

  @DgsMutation
  public int deleteHello(@InputArgument(name = "number") Integer number) {
    var hellos = new ArrayList<>(dataSource.getHelloList());
    dataSource.getHelloList().forEach(hello -> {
      if (Objects.equals(hello.getRandomNumber(), number)) {
        hellos.remove(hello);
      }
    });
    dataSource.setHelloList(hellos);

    return dataSource.getHelloList().size();
  }

}
