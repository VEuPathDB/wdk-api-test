package org.gusdb.wdk.model.api.record.search.reporter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.gusdb.wdk.model.api.SortSpec;

import java.util.Collection;

public final class ReportMeta {
  private long displayViewTotalCount;
  private long viewTotalCount;
  private long responseCount;
  private long totalCount;
  private long displayTotalCount;
  private String recordClassName;
  private ReportPagination pagination;
  private Collection<ObjectNode> tables;
  private Collection<SortSpec> sorting;
  private Collection<String> attributes;

  public long getDisplayViewTotalCount() {
    return displayViewTotalCount;
  }

  public void setDisplayViewTotalCount(long displayViewTotalCount) {
    this.displayViewTotalCount = displayViewTotalCount;
  }

  public long getViewTotalCount() {
    return viewTotalCount;
  }

  public void setViewTotalCount(long viewTotalCount) {
    this.viewTotalCount = viewTotalCount;
  }

  public long getResponseCount() {
    return responseCount;
  }

  public void setResponseCount(long responseCount) {
    this.responseCount = responseCount;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

  public long getDisplayTotalCount() {
    return displayTotalCount;
  }

  public void setDisplayTotalCount(long displayTotalCount) {
    this.displayTotalCount = displayTotalCount;
  }

  public String getRecordClassName() {
    return recordClassName;
  }

  public void setRecordClassName(String recordClassName) {
    this.recordClassName = recordClassName;
  }

  public ReportPagination getPagination() {
    return pagination;
  }

  public void setPagination(ReportPagination pagination) {
    this.pagination = pagination;
  }

  public Collection<ObjectNode> getTables() {
    return tables;
  }

  public void setTables(Collection<ObjectNode> tables) {
    this.tables = tables;
  }

  public Collection<SortSpec> getSorting() {
    return sorting;
  }

  public void setSorting(Collection<SortSpec> sorting) {
    this.sorting = sorting;
  }

  public Collection<String> getAttributes() {
    return attributes;
  }

  public void setAttributes(Collection<String> attributes) {
    this.attributes = attributes;
  }
}
