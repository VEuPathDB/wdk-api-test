package org.gusdb.wdk.model.api.record.search.reporter;

public final class ReportPagination {
  private long offset;
  private long numRecords;

  public long getOffset() {
    return offset;
  }

  public void setOffset(long offset) {
    this.offset = offset;
  }

  public long getNumRecords() {
    return numRecords;
  }

  public void setNumRecords(long numRecords) {
    this.numRecords = numRecords;
  }
}
