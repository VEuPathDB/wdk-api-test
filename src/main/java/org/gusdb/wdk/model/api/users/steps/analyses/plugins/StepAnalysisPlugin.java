package org.gusdb.wdk.model.api.users.steps.analyses.plugins;

import java.util.Objects;

public abstract class StepAnalysisPlugin<T> {
  protected T formParams;
  protected String displayName;

  public T getFormParams() {
    return formParams;
  }

  public StepAnalysisPlugin<T> setFormParams(T formParams) {
    this.formParams = formParams;
    return this;
  }

  public String getDisplayName() {
    return displayName;
  }

  public StepAnalysisPlugin<T> setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      return true;
    }

    if(!(obj instanceof StepAnalysisPlugin)) {
      return false;
    }

    final StepAnalysisPlugin tst = (StepAnalysisPlugin) obj;

    return Objects.equals(displayName, tst.displayName)
        && Objects.equals(formParams, tst.formParams);
  }
}
