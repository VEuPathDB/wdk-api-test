package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisSummary {
  @JsonProperty("analysisId")
  private long id;

  private String displayName;

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "AnalysisSummary{" + "id=" + id + '}';
  }
}
