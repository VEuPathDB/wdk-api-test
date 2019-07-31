package test.wdk.recordtype.search.columns;

import org.gusdb.wdk.model.api.ColumnReporterConfig;
import org.gusdb.wdk.model.api.SearchConfig;

public class DefaultColumnReportRequest {

  private SearchConfig searchConfig;
  private Object reportConfig; // can be anything

  public DefaultColumnReportRequest(SearchConfig searchConfig) {
    this(searchConfig, new ColumnReporterConfig());
  }

  public DefaultColumnReportRequest(SearchConfig searchConfig, ColumnReporterConfig reportConfig) {
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
