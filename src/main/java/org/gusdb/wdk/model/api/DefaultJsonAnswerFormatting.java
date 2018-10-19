package org.gusdb.wdk.model.api;

public class DefaultJsonAnswerFormatting {
  private String reporterName;
  private DefaultJsonAnswerFormatConfig formatConfig;
  
  public DefaultJsonAnswerFormatting(String reporterName, DefaultJsonAnswerFormatConfig formatConfig) {
    this.reporterName = reporterName;
    this.formatConfig = formatConfig;
  }
  
  public String getReporterName() {
    return reporterName;
  }
  public void setReporterName(String reporterName) {
    this.reporterName = reporterName;
  }
  public DefaultJsonAnswerFormatConfig getFormatConfig() {
    return formatConfig;
  }
  public void setFormatConfig(DefaultJsonAnswerFormatConfig formatConfig) {
    this.formatConfig = formatConfig;
  }
  
  
}
