package com.example.graphql;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.apache.commons.lang3.StringUtils;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeHelloDataResolverTest {

  @Autowired
  private DgsQueryExecutor executor;

  @Test
  void testOneHello() {
    @Language("GraphQL") var query = """
            {
               oneHello {
                 randomNumber
                 text
               }
             }
            """;

    String text = executor.executeAndExtractJsonPath(query, "data.oneHello.text");
    Integer randomNumber = executor.executeAndExtractJsonPath(query, "data.oneHello.randomNumber");

    assertFalse(StringUtils.isBlank(text));
    assertNotNull(randomNumber);
  }

  @Test
  void testAllHellos() {
    @Language("GraphQL") var query = """
            {
               allHellos {
                 randomNumber
                 text
               }
             }
            """;

    List<String> texts = executor.executeAndExtractJsonPath(query, "data.allHellos[*].text");
    List<Integer> randomNumbers = executor.executeAndExtractJsonPath(query, "data.allHellos[*].randomNumber");

    assertNotNull(texts);
    assertFalse(texts.isEmpty());
    assertNotNull(randomNumbers);
    assertFalse(randomNumbers.isEmpty());
    assertEquals(texts.size(), randomNumbers.size());
  }
}
