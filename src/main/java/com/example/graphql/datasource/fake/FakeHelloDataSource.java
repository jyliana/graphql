package com.example.graphql.datasource.fake;

import com.example.graphql.codegen.types.Hello;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class FakeHelloDataSource {

  private Faker faker;

  private List<Hello> createRandomList() {
    return IntStream.range(0, 20).mapToObj(h -> Hello.newBuilder()
            .randomNumber(faker.random().nextInt(5000))
            .text(faker.company().industry())
            .build()
    ).toList();
  }

  public List<Hello> getHelloList() {
    return createRandomList();
  }
}
