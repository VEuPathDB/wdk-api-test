package org.gusdb.wdk.model.api.users.steps.analyses.plugins;

public class NewAnalysisRequest {
  public final String analysisName;

  public NewAnalysisRequest(String analysisName) {
    this.analysisName = analysisName;
  }

  public String getAnalysisName() {
    return analysisName;
  }
}
