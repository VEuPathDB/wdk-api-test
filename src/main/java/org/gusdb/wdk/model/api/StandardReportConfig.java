package org.gusdb.wdk.model.api;

import java.util.ArrayList;
import java.util.List;

public class StandardReportConfig extends BaseStandardReportConfig {

  ReportPagination pagination;

  List<SortSpec> sorting;

  public ReportPagination getPagination() {
    return pagination;
  }

  public void setPagination(ReportPagination pagination) {
    this.pagination = pagination;
  }

  public List<SortSpec> getSorting() {
    return sorting;
  }

  public void setSorting(List<SortSpec> sorting) {
    this.sorting = sorting;
  }

  public StandardReportConfig addAttribute(String attr) {
    if (attributes == null)
      attributes = new ArrayList<>();

    attributes.add(attr);

    return this;
  }
}
