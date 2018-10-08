package org.gusdb.wdk.model.api;

public class AnswerFormatting {
  private String format;
  private Object formatConfig;
  
  public AnswerFormatting(String format) {
    this.format = format;
  }
  
  public String getFormat() {
    return format;
  }
  public void setFormat(String format) {
    this.format = format;
  }
  public Object getFormatConfig() {
    return formatConfig;
  }
  public void setFormatConfig(Object formatConfig) {
    this.formatConfig = formatConfig;
  }
  
}
