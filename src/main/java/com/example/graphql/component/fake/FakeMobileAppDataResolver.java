package com.example.graphql.component.fake;

import com.example.graphql.codegen.DgsConstants;
import com.example.graphql.codegen.types.MobileApp;
import com.example.graphql.codegen.types.MobileAppFilter;
import com.example.graphql.datasource.fake.FakeMobileAppDataSource;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DgsComponent
@AllArgsConstructor
public class FakeMobileAppDataResolver {

  private FakeMobileAppDataSource dataSource;

  @DgsQuery(field = DgsConstants.QUERY.MobileApps)
  public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter") Optional<MobileAppFilter> filter) {
    var mobileAppList = dataSource.getMobileAppList();
    return filter.isEmpty() ?
            mobileAppList
            : mobileAppList.stream().filter(mobileApp -> matchFilter(filter.get(), mobileApp)).toList();
  }

  private boolean matchFilter(MobileAppFilter filter, MobileApp app) {
    var isAppMatch = StringUtils.containsIgnoreCase(app.getName(), StringUtils.defaultIfBlank(filter.getName(), StringUtils.EMPTY))
            && StringUtils.containsIgnoreCase(app.getVersion(), StringUtils.defaultIfBlank(filter.getVersion(), StringUtils.EMPTY))
            && app.getReleaseDate().isAfter(Optional.ofNullable(filter.getReleasedAfter()).orElse(LocalDate.MIN))
            && app.getDownloaded() >= Optional.ofNullable(filter.getMinimumDownload()).orElse(0);

    if (!isAppMatch) {
      return false;
    }

    if (StringUtils.isNotBlank(filter.getPlatform()) && !app.getPlatform().contains(filter.getPlatform().toLowerCase())) {
      return false;
    }
    if (filter.getAuthor() != null
            && !StringUtils.containsIgnoreCase(app.getAuthor().getName(),
            StringUtils.defaultIfBlank(filter.getAuthor().getName(), StringUtils.EMPTY))) {
      return false;
    }

    if (filter.getCategory() != null && !app.getCategory().equals(filter.getCategory())) {
      return false;
    }

    return true;
  }
}
