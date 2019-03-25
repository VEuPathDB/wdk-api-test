package org.gusdb.wdk.model.api;

public class StepTreeNode {
  Long stepId;
  StepTreeNode primaryInput;
  StepTreeNode secondaryInput;
  
  public StepTreeNode(long stepId) {
    this.stepId = stepId;
  }
  
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
  
  // a flattened version also guaranteed to be unique for a step tree node
  public String toString() {
    StringBuilder builder = new StringBuilder();
    buildString(builder);
    return builder.toString();
  }
  
  // (stepId (stepId null null) (stepId (stepId null null) null))
  private void buildString(StringBuilder builder) {
    builder.append("(").append(stepId).append(" ");
    
    if (primaryInput == null) builder.append("null");
    else primaryInput.buildString(builder);
    builder.append(" ");
    
    if (secondaryInput == null) builder.append("null");
    else secondaryInput.buildString(builder);
    builder.append(")");
  }

  public boolean equals(StepTreeNode tree) {
    return tree.toString().equals(toString());
  } 
}
