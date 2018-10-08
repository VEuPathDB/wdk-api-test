package org.gusdb.wdk.model.api;

import java.util.List;

public class DefaultJsonAnswerFormatConfig extends StandardAnswerFormatConfig {

  Pagination pagination;
  List<SortSpec> sorting;
  public Pagination getPagination() {
    return pagination;
  }
  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }
  public List<SortSpec> getSorting() {
    return sorting;
  }
  public void setSorting(List<SortSpec> sorting) {
    this.sorting = sorting;
  }

}
