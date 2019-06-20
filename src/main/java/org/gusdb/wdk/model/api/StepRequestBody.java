package org.gusdb.wdk.model.api;

public class StepRequestBody extends StepMeta {

  SearchConfig searchConfig;
  String searchName;

  public StepRequestBody() {
    super();
  }

  public StepRequestBody(SearchConfig searchConfig, String searchName) {
    super();
    this.searchConfig = searchConfig;
    this.searchName = searchName;
  }

  public SearchConfig getSearchConfig() {
    return searchConfig;
  }

  public void setSearchConfig(SearchConfig searchConfig) {
    this.searchConfig = searchConfig;
  }

  public String getSearchName() {
    return searchName;
  }

  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }

}
