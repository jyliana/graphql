package com.example.graphql.datasource.fake;

import com.example.graphql.types.Address;
import com.example.graphql.types.Author;
import com.example.graphql.types.MobileApp;
import com.example.graphql.types.MobileAppCategory;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Configuration
@AllArgsConstructor
public class FakeMobileAppDataSource {

  private Faker faker;
  @Getter
  private List<MobileApp> mobileAppList;

  @PostConstruct
  private void createRandomMobileAppList() throws RuntimeException {
    mobileAppList = IntStream.range(0, 20).mapToObj(h -> {
      var addresses = new ArrayList<Address>();
      var author = Author.newBuilder().addresses(addresses)
              .name(faker.book().author())
              .originCountry(faker.country().name())
              .build();
      var mobileApp = MobileApp.newBuilder()
              .name(faker.app().name())
              .author(author)
              .version(faker.app().version())
              .platform(randomMobileAppPlatform())
              .appId(UUID.randomUUID().toString())
              .releaseDate(LocalDate.now().minusDays(faker.random().nextInt(365)))
              .downloaded(faker.number().numberBetween(1, 1_500_000))
              .homepage(getUrl())
              .category(MobileAppCategory.values()[
                      faker.random().nextInt(MobileAppCategory.values().length)]
              )
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
      return mobileApp;
    }).toList();
  }

  @NotNull
  private URL getUrl() {
    try {
      return new URL(faker.internet().url());
    } catch (MalformedURLException e) {
      throw new RuntimeException("something happened");
    }

  }

  private List<String> randomMobileAppPlatform() {
    return switch (ThreadLocalRandom.current().nextInt(10) % 3) {
      case 0 -> List.of("android");
      case 1 -> List.of("ios");
      default -> List.of("ios", "android");
    };
  }

}
