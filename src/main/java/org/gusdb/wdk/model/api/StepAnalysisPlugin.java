package org.gusdb.wdk.model.api;

abstract class StepAnalysisPlugin < T > {
  protected T formParams;
  protected String displayName;

  public T getFormParams() {
    return formParams;
  }

  public void setFormParams(T formParams) {
    this.formParams = formParams;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
