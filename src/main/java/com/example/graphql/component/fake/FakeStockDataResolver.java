package com.example.graphql.component.fake;

import com.example.graphql.datasource.fake.FakeStockDataSource;
import com.example.graphql.types.Stock;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DgsComponent
@AllArgsConstructor
public class FakeStockDataResolver {

  private FakeStockDataSource dataSource;

  @DgsSubscription
  public Publisher<Stock> randomStock() {
    return Flux.interval(Duration.ofSeconds(3)).map(stock -> dataSource.randomStock());
  }
}
