package org.gusdb.wdk.model.api;

import java.util.Map;

public class FilterValueSpec {
  String name;
  Map<String, Object> value;
  boolean disabled = false;
  
  public boolean getDisabled() {
    return disabled;
  }
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Map<String, Object> getValue() {
    return value;
  }
  public void setValue(Map<String, Object> value) {
    this.value = value;
  }

}
