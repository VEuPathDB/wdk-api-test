package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SortSpec {

  public enum SortDirection {
    ASC, DESC;
  }

  String attributeName;
  SortDirection sortDirection;

  public String getAttributeName() {
    return attributeName;
  }

  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }

  public SortDirection getSortDirection() {
    return sortDirection;
  }

  @JsonAlias("direction")
  public void setSortDirection(SortDirection sortDirection) {
    this.sortDirection = sortDirection;
  }
}
