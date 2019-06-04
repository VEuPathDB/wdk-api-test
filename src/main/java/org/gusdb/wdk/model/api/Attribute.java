package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Attribute {
  public String help;
  public Boolean isInReport;
  public Format[] formats;
  public Integer truncateTo;
  public String displayName;
  public String name;
  public Boolean isSortable;
  public Boolean isDisplayable;
  public Boolean isRemovable;
  public String columnDataType;
  public AttributeToolBundle tools;
  public ObjectNode properties;
  public String align;
  public String type;

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Attribute{name=\""+name+"\"}";
  }
}
