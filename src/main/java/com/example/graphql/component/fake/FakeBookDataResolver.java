package com.example.graphql.component.fake;

import com.example.graphql.codegen.DgsConstants;
import com.example.graphql.codegen.types.Book;
import com.example.graphql.codegen.types.ReleaseHistory;
import com.example.graphql.codegen.types.ReleaseHistoryInput;
import com.example.graphql.datasource.fake.FakeBookDataSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@DgsComponent
@AllArgsConstructor
public class FakeBookDataResolver {

  FakeBookDataSource dataSource;

  @DgsQuery(field = "books")
  public List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String> authorName) {
    return dataSource.getBookList().stream()
            .filter(book -> authorName.isEmpty() || StringUtils.isBlank(authorName.get()) || StringUtils.containsIgnoreCase(book.getAuthor().getName(), authorName.get()))
            .toList();
  }

  @DgsQuery(field = DgsConstants.QUERY.BooksByReleased)
  public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
    var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
    var releasedInput = ReleaseHistoryInput.newBuilder()
            .printedEdition((boolean) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
            .year((int) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
            .build();
    return dataSource.getBookList().stream().filter(book -> matchReleasedHistory(releasedInput, book.getReleased())).toList();
  }


  private boolean matchReleasedHistory(ReleaseHistoryInput input, ReleaseHistory element) {
    return input.getPrintedEdition().equals(element.getPrintedEdition()) &&
            input.getYear() == element.getYear();
  }
}
