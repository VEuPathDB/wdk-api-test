package org.gusdb.wdk.model.api;

public class DefaultAnswerReportRequest {
  private AnswerSpec answerSpec;
  private DefaultJsonAnswerFormatting formatting;
  
  public DefaultAnswerReportRequest(AnswerSpec answerSpec, DefaultJsonAnswerFormatting formatting) {
    this.answerSpec = answerSpec;
    this.formatting = formatting;
  }
  
  public AnswerSpec getAnswerSpec() {
    return answerSpec;
  }
  public void setAnswerSpec(AnswerSpec answerSpec) {
    this.answerSpec = answerSpec;
  }
  public DefaultJsonAnswerFormatting getFormatting() {
    return formatting;
  }
  public void setFormatting(DefaultJsonAnswerFormatting formatting) {
    this.formatting = formatting;
  }

  

}
