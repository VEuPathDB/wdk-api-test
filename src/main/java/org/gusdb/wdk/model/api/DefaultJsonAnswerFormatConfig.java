package org.gusdb.wdk.model.api;

import java.util.List;

public class DefaultJsonAnswerFormatConfig extends StandardAnswerFormatConfig {

  AnswerPagination pagination;
  List<SortSpec> sorting;
  public AnswerPagination getPagination() {
    return pagination;
  }
  public void setPagination(AnswerPagination pagination) {
    this.pagination = pagination;
  }
  public List<SortSpec> getSorting() {
    return sorting;
  }
  public void setSorting(List<SortSpec> sorting) {
    this.sorting = sorting;
  }

}
