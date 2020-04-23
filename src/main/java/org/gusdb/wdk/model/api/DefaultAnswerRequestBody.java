package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DefaultAnswerRequestBody {
  @JsonProperty("searchConfig")
  private AnswerSpec answerSpec;

  @JsonProperty("reportConfig")
  private DefaultJsonAnswerFormatConfig formatConfig;


  public DefaultAnswerRequestBody(AnswerSpec answerSpec) {
    this.answerSpec = answerSpec;
  }

  public AnswerSpec getAnswerSpec() {
    return answerSpec;
  }
  public void setAnswerSpec(AnswerSpec answerSpec) {
    this.answerSpec = answerSpec;
  }
  public DefaultJsonAnswerFormatConfig getFormatConfig() {
    return formatConfig;
  }
  public void setFormatConfig(DefaultJsonAnswerFormatConfig formatConfig) {
    this.formatConfig = formatConfig;
  }

  private List<FilterValueSpec> viewFilters = new ArrayList<>();
  public List<FilterValueSpec> getViewFilters() { return viewFilters; }
  public void setViewFilters(List<FilterValueSpec> vf) { viewFilters = vf; }
}
