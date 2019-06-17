package org.gusdb.wdk.model.api.record.search.reporter;

import java.util.Collection;

public class JsonReporterResponse
{
  private Collection<JsonReportRecord> records;

  private ReportMeta meta;

  public Collection<JsonReportRecord> getRecords() {
    return records;
  }

  public void setRecords(Collection<JsonReportRecord> records) {
    this.records = records;
  }

  public ReportMeta getMeta() {
    return meta;
  }

  public void setMeta(ReportMeta meta) {
    this.meta = meta;
  }
}
