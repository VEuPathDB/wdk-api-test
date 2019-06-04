package org.gusdb.wdk.model.api;

import java.util.Collection;

public class ReportConfig {
  public ReportPagination pagination;
  public Collection<String> attributes;
  public Collection<String> table;
  public SortSpec sorting;

  public ReportConfig setPagination(ReportPagination pagination) {
    this.pagination = pagination;
    return this;
  }

  public ReportConfig setAttributes(Collection<String> attributes) {
    this.attributes = attributes;
    return this;
  }

  public ReportConfig setTable(Collection<String> table) {
    this.table = table;
    return this;
  }

  public ReportConfig setSorting(SortSpec sorting) {
    this.sorting = sorting;
    return this;
  }
}
