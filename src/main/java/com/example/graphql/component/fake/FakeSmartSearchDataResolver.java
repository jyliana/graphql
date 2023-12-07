package com.example.graphql.component.fake;

import com.example.graphql.DgsConstants;
import com.example.graphql.datasource.fake.FakeBookDataSource;
import com.example.graphql.datasource.fake.FakeHelloDataSource;
import com.example.graphql.types.SmartSearchResult;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
@AllArgsConstructor
public class FakeSmartSearchDataResolver {
  private FakeHelloDataSource helloDataSource;
  private FakeBookDataSource bookDataSource;

  @DgsQuery(field = DgsConstants.QUERY.SmartSearch)
  public List<SmartSearchResult> getSmartSearchResult(@InputArgument(name = "keyword") Optional<String> keyword) {
    var result = new ArrayList<SmartSearchResult>();

    if (keyword.isEmpty()) {
      result.addAll(helloDataSource.getHelloList());
      result.addAll(bookDataSource.getBookList());
    } else {
      String keywordString = keyword.get();
      helloDataSource.getHelloList().stream().filter(hello -> StringUtils.containsIgnoreCase(hello.getText(), keywordString)).forEach(result::add);
      bookDataSource.getBookList().stream().filter(book -> StringUtils.containsIgnoreCase(book.getTitle(), keywordString)).forEach(result::add);
    }

    return result;
  }
}
