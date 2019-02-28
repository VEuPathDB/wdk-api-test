package org.gusdb.wdk.model.api;
import java.util.List;

public class BaseStandardReportConfig {
  List<String> attributes;
  List<String> tables;
  String attachmentType;
  Boolean includeEmptyTables;
  public List<String> getAttributes() {
    return attributes;
  }
  public void setAttributes(List<String> attributes) {
    this.attributes = attributes;
  }
  public List<String> getTables() {
    return tables;
  }
  public void setTables(List<String> tables) {
    this.tables = tables;
  }
  public String getAttachmentType() {
    return attachmentType;
  }
  public void setAttachmentType(String attachmentType) {
    this.attachmentType = attachmentType;
  }
  public Boolean getIncludeEmptyTables() {
    return includeEmptyTables;
  }
  public void setIncludeEmptyTables(Boolean includeEmptyTables) {
    this.includeEmptyTables = includeEmptyTables;
  } 
}
