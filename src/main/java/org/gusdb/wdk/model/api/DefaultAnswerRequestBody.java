package org.gusdb.wdk.model.api;

public class DefaultAnswerRequestBody {
  private AnswerSpec answerSpec;
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
  
  
}
