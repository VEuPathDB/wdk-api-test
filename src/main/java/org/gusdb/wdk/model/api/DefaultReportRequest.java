package org.gusdb.wdk.model.api;

public class DefaultReportRequest {

  private SearchConfig searchConfig;
  private StandardReportConfig reportConfig;

  public DefaultReportRequest(SearchConfig searchConfig, StandardReportConfig reportConfig) {
    this.searchConfig = searchConfig;
    this.reportConfig = reportConfig;
  }

  public SearchConfig getSearchConfig() {
    return searchConfig;
  }
  public void setSearchConfig(SearchConfig searchConfig) {
    this.searchConfig = searchConfig;
  }
  public StandardReportConfig getReportConfig() {
    return reportConfig;
  }
  public void setFormatting(StandardReportConfig reportConfig) {
    this.reportConfig = reportConfig;
  }
}
