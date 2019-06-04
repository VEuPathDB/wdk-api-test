package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Table {
  public Boolean isInReport;
  public String displayName;
  public String name;
  public String description;
  public Attribute[] attributes;
  public Boolean isDisplayable;
  public ObjectNode properties;
  public ArrayNode clientSortSpec; // Not sure what this looks like
}
