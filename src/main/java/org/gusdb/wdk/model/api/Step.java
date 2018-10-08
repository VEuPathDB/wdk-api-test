package org.gusdb.wdk.model.api;

public class Step {
  String customName;
  String collapsedName;
  Boolean isCollapsed;
  AnswerSpec answerSpec;
  AnswerFormatting answerFormatting;
  
  public Step(AnswerSpec answerSpec, AnswerFormatting answerFormatting) {
    this.answerSpec = answerSpec;
    this.answerFormatting = answerFormatting;
  }
  
  public AnswerFormatting getAnswerFormatting() {
    return answerFormatting;
  }

  public void setAnswerFormatting(AnswerFormatting answerFormatting) {
    this.answerFormatting = answerFormatting;
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
  
  
}
