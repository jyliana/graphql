package com.example.graphql.component.fake;

import com.example.graphql.codegen.types.Book;
import com.example.graphql.datasource.fake.FakeBookDataSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;

import java.util.List;
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

}
