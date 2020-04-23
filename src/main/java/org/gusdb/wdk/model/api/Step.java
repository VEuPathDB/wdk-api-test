package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Step {
  String customName;
  String collapsedName;
  Boolean isCollapsed;

  String searchName;

  @JsonProperty("searchConfig")
  AnswerSpec answerSpec;

  public Step(AnswerSpec answerSpec) {
    this.answerSpec = answerSpec;
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
  public AnswerSpec getAnswerSpec() {
    return answerSpec;
  }
  public void setAnswerSpec(AnswerSpec answerSpec) {
    this.answerSpec = answerSpec;
  }

  public String getSearchName() {
    return searchName;
  }

  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }
}
