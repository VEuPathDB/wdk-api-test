package org.gusdb.wdk.model.api;

import java.util.ArrayList;
import java.util.List;

public class BaseStandardReportConfig {
  List<String> attributes;

  List<String> tables;

  String attachmentType;

  Boolean includeEmptyTables;

  public List<String> getAttributes() {
    return attributes;
  }

  public BaseStandardReportConfig setAttributes(List<String> attributes) {
    this.attributes = attributes;
    return this;
  }

  public BaseStandardReportConfig addAttribute(String attr) {
    if (null == this.attributes)
      this.attributes = new ArrayList<>();
    this.attributes.add(attr);
    return this;
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
