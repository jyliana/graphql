package com.example.graphql.datasource.fake;

import com.example.graphql.types.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
@Getter
public class FakePetDataSource {

  private Faker faker;
  private List<Pet> petlist;

  @PostConstruct
  private void postConstruct() {
    for (int i = 0; i < 10; i++) {
      var animal = switch (i % 2) {
        case 0 -> Dog.newBuilder().name(faker.dog().name())
                .food(PetFoodType.OMNIVORE)
                .breed(faker.dog().breed())
                .size(faker.dog().size())
                .coatLength(faker.dog().coatLength())
                .build();

        default -> Cat.newBuilder().name(faker.cat().name())
                .food(PetFoodType.CARNIVORE)
                .breed(faker.cat().breed())
                .registry(faker.cat().registry())
                .build();
      };

      petlist.add(animal);
    }
  }

}
