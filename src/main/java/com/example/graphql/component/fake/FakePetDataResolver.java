package com.example.graphql.component.fake;

import com.example.graphql.DgsConstants;
import com.example.graphql.datasource.fake.FakePetDataSource;
import com.example.graphql.types.Cat;
import com.example.graphql.types.Dog;
import com.example.graphql.types.Pet;
import com.example.graphql.types.PetFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@DgsComponent
@AllArgsConstructor
public class FakePetDataResolver {
  private FakePetDataSource dataSource;

  @DgsQuery(field = DgsConstants.QUERY.Pets)
  public List<Pet> getPets(@InputArgument(name = "petFilter") Optional<PetFilter> filter) {
    var petlist = dataSource.getPetlist();
    return filter.isEmpty() ?
            petlist
            : petlist.stream().filter(pet -> matchFilter(filter.get(), pet)).toList();
  }

  private boolean matchFilter(PetFilter filter, Pet pet) {
    if (StringUtils.isEmpty(filter.getPetType())) {
      return true;
    }
    if (filter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())) {
      return pet instanceof Dog;
    } else if (filter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())) {
      return pet instanceof Cat;
    } else return false;
  }
}
