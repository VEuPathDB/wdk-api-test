package org.gusdb.wdk.model.api;

public class SortSpec {
  
  public enum SortDirection {
    ASC,
    DESC;
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
  public void setSortDirection(SortDirection sortDirection) {
    this.sortDirection = sortDirection;
  }
  
  
}
