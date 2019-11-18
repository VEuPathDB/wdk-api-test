package org.gusdb.wdk.model.api;

import java.util.ArrayList;
import java.util.List;

public class DefaultReportRequest {

  private SearchConfig searchConfig;
  private List<FilterValueSpec> viewFilters;
  private StandardReportConfig reportConfig;

  public DefaultReportRequest(SearchConfig searchConfig, StandardReportConfig reportConfig) {
    this(searchConfig, new ArrayList<>(), reportConfig);
  }

  public DefaultReportRequest(SearchConfig searchConfig,
      List<FilterValueSpec> viewFilters, StandardReportConfig reportConfig) {
    this.searchConfig = searchConfig;
    this.viewFilters = viewFilters;
    this.reportConfig = reportConfig;
  }

  public SearchConfig getSearchConfig() {
    return searchConfig;
  }
  public void setSearchConfig(SearchConfig searchConfig) {
    this.searchConfig = searchConfig;
  }
  public List<FilterValueSpec> getViewFilters() {
    return viewFilters;
  }
  public void setViewFilters(List<FilterValueSpec> viewFilters) {
    this.viewFilters = viewFilters;
  }
  public StandardReportConfig getReportConfig() {
    return reportConfig;
  }
  public void setFormatting(StandardReportConfig reportConfig) {
    this.reportConfig = reportConfig;
  }
}
