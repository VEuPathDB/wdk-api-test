package org.gusdb.wdk.model.api;

public class StepTreeNode {
  Long stepId;
  StepTreeNode primaryInput;
  StepTreeNode secondaryInput;
  public Long getStepId() {
    return stepId;
  }
  public void setStepId(Long stepId) {
    this.stepId = stepId;
  }
  public StepTreeNode getPrimaryInput() {
    return primaryInput;
  }
  public void setPrimaryInput(StepTreeNode primaryInput) {
    this.primaryInput = primaryInput;
  }
  public StepTreeNode getSecondaryInput() {
    return secondaryInput;
  }
  public void setSecondaryInput(StepTreeNode secondaryInput) {
    this.secondaryInput = secondaryInput;
  }
}
