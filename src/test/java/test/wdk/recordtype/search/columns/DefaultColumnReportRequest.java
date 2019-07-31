package test.wdk.recordtype.search.columns;

import org.gusdb.wdk.model.api.SearchConfig;

public class DefaultColumnReportRequest {

  private SearchConfig searchConfig;
  private Object reportConfig; // can be anything

  public DefaultColumnReportRequest(SearchConfig searchConfig) {
    this(searchConfig, new Object());
  }

  public DefaultColumnReportRequest(SearchConfig searchConfig, Object reportConfig) {
    this.searchConfig = searchConfig;
    this.reportConfig = reportConfig;
  }

  public SearchConfig getSearchConfig() {
    return searchConfig;
  }

  public Object getReportConfig() {
    return reportConfig;
  }
}
