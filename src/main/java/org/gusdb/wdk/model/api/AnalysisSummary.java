package org.gusdb.wdk.model.api;

public class AnalysisSummary {
  private long analysisId;
  private String displayName;

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public long getAnalysisId() {
    return analysisId;
  }

  public void setAnalysisId(long analysisId) {
    this.analysisId = analysisId;
  }

  @Override
  public String toString() {
    return "AnalysisSummary{" + "analysisId=" + analysisId + '}';
  }
}
