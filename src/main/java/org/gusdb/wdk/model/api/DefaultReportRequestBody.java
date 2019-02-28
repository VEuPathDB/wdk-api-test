package org.gusdb.wdk.model.api;

public class DefaultReportRequestBody {
  private SearchConfig searchConfig;
  private StandardReportConfig reportConfig;
  
  public DefaultReportRequestBody(SearchConfig searchConfig) {
    this.searchConfig = searchConfig;
  }
  
  public SearchConfig getSearchConfig() {
    return searchConfig;
  }
  public void setAnswerSpec(SearchConfig searchConfig) {
    this.searchConfig = searchConfig;
  }
  public StandardReportConfig getReportConfig() {
    return reportConfig;
  }
  public void setFormatConfig(StandardReportConfig reportConfig) {
    this.reportConfig = reportConfig;
  }
  
  
}
