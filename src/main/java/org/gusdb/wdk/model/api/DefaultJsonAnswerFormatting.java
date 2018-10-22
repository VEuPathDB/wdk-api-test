package org.gusdb.wdk.model.api;

public class DefaultJsonAnswerFormatting {
  private String format;
  private DefaultJsonAnswerFormatConfig formatConfig;
  
  public DefaultJsonAnswerFormatting(String format, DefaultJsonAnswerFormatConfig formatConfig) {
    this.format = format;
    this.formatConfig = formatConfig;
  }
  
  public String getFormat() {
    return format;
  }
  public void setFormat(String format) {
    this.format = format;
  }
  public DefaultJsonAnswerFormatConfig getFormatConfig() {
    return formatConfig;
  }
  public void setFormatConfig(DefaultJsonAnswerFormatConfig formatConfig) {
    this.formatConfig = formatConfig;
  }
  
  
}
