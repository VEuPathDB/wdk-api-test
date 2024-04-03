package org.gusdb.wdk.model.api.users.steps.analyses.plugins;

import java.util.Objects;

public class StepAnalysisPluginResponse<T> extends StepAnalysisPlugin<T> {
  private String answerValueHash;
  private long stepId;
  private long analysisId;
  private String description;
  private String shortDescription;
  private boolean hasParams;
  private String invalidStepReason;
  private String userNotes;
  private String analysisName;
  private String status;

  public String getAnswerValueHash() {
    return answerValueHash;
  }

  public void setAnswerValueHash(String answerValueHash) {
    this.answerValueHash = answerValueHash;
  }

  public long getStepId() {
    return stepId;
  }

  public void setStepId(long stepId) {
    this.stepId = stepId;
  }

  public long getAnalysisId() {
    return analysisId;
  }

  public void setAnalysisId(long analysisId) {
    this.analysisId = analysisId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public boolean isHasParams() {
    return hasParams;
  }

  public void setHasParams(boolean hasParams) {
    this.hasParams = hasParams;
  }

  public String getInvalidStepReason() {
    return invalidStepReason;
  }

  public void setInvalidStepReason(String invalidStepReason) {
    this.invalidStepReason = invalidStepReason;
  }

  public String getUserNotes() {
    return userNotes;
  }

  public void setUserNotes(String userNotes) {
    this.userNotes = userNotes;
  }

  public String getAnalysisName() {
    return analysisName;
  }

  public void setAnalysisName(String analysisName) {
    this.analysisName = analysisName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if(!super.equals(obj) || !(obj instanceof StepAnalysisPluginResponse)) {
      return false;
    }

    final StepAnalysisPluginResponse<?> tst = (StepAnalysisPluginResponse<?>) obj;

    return Objects.equals(answerValueHash, tst.answerValueHash)
        && stepId == tst.stepId
        && analysisId == tst.analysisId
        && Objects.equals(description, tst.description)
        && Objects.equals(shortDescription, tst.shortDescription)
        && hasParams == tst.hasParams
        && Objects.equals(invalidStepReason, tst.invalidStepReason)
        && Objects.equals(userNotes, tst.userNotes)
        && Objects.equals(analysisName, tst.analysisName)
        && Objects.equals(status, tst.status);
  }
}
