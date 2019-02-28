package org.gusdb.wdk.model.api;

public class Step {
  String customName;
  String collapsedName;
  Boolean isCollapsed;
  SearchConfig searchConfig;
  
  public Step(SearchConfig searchConfig) {
    this.searchConfig = searchConfig;
  }
  
  public String getCustomName() {
    return customName;
  }
  public void setCustomName(String customName) {
    this.customName = customName;
  }
  public String getCollapsedName() {
    return collapsedName;
  }
  public void setCollapsedName(String collapsedName) {
    this.collapsedName = collapsedName;
  }
  public Boolean getIsCollapsed() {
    return isCollapsed;
  }
  public void setIsCollapsed(Boolean isCollapsed) {
    this.isCollapsed = isCollapsed;
  }
  public SearchConfig getSearchConfig() {
    return searchConfig;
  }
  public void setSearchConfig(SearchConfig searchConfig) {
    this.searchConfig = searchConfig;
  }
  
  
}
