package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultAnswerReportRequest {
  @JsonProperty("searchConfig")
  private AnswerSpec answerSpec;

  @JsonProperty("reportConfig")
  private Object formatting;

  public DefaultAnswerReportRequest(AnswerSpec answerSpec, Object formatting) {
    this.answerSpec = answerSpec;
    this.formatting = formatting;
  }

  public AnswerSpec getAnswerSpec() {
    return answerSpec;
  }
  public void setAnswerSpec(AnswerSpec spec) { answerSpec = spec; }

  public Object getFormatting() {
    return formatting;
  }
  public void setFormatting(Object format) { formatting = format; }
}
