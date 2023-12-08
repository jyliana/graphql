package com.example.graphql.datasource.fake;

import com.example.graphql.types.Stock;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

@Configuration
@AllArgsConstructor
public class FakeStockDataSource {
  private Faker faker;

  public Stock randomStock() {
    return Stock.newBuilder()
            .price(BigDecimal.valueOf(faker.random().nextDouble(100, 1000))
                    .setScale(4, RoundingMode.HALF_UP)
                    .doubleValue())
            .symbol(faker.stock().nsdqSymbol())
            .lastTradeDateTime(OffsetDateTime.now().minusHours(2))
            .build();
  }
}
