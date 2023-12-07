package com.example.graphql.datasource.fake;

import com.example.graphql.types.Hello;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class FakeHelloDataSource {

  private Faker faker;
  @Getter
  @Setter
  private List<Hello> helloList;

  @PostConstruct
  public void createRandomList() {
    helloList = IntStream.range(0, 20).mapToObj(h -> Hello.newBuilder()
            .randomNumber(faker.random().nextInt(5000))
            .text(faker.company().industry())
            .build()
    ).toList();
  }
}
