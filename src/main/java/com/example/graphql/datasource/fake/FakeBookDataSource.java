package com.example.graphql.datasource.fake;

import com.example.graphql.codegen.types.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Configuration
@AllArgsConstructor
public class FakeBookDataSource {

  private Faker faker;
  private List<Book> booklist;

  @PostConstruct
  private void createRandomBookList() {
    booklist = IntStream.range(0, 20).mapToObj(h -> {
      var addresses = new ArrayList<Address>();
      var author = Author.newBuilder().addresses(addresses)
              .name(faker.book().author())
              .originCountry(faker.country().name())
              .build();
      var released = ReleaseHistory.newBuilder()
              .printedEdition(faker.bool().bool())
              .releasedCountry(faker.country().name())
              .year(faker.number().numberBetween(2000, 2023))
              .build();
      var book = Book.newBuilder().author(author)
              .publisher(faker.book().publisher())
              .title(faker.book().title())
              .released(released)
              .build();

      for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, 3); j++) {
        var address = Address.newBuilder()
                .city(faker.address().cityName())
                .country(faker.address().country())
                .street(faker.address().streetAddress())
                .zipCode(faker.address().zipCode())
                .build();

        addresses.add(address);
      }
      return book;
    }).toList();
  }

  public List<Book> getBookList() {
    return booklist;
  }
}
