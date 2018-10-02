package org.gusdb.wdk.model.api;

import java.util.Map;

public class FilterValueSpec {
  String name;
  Map<String, Object> value;
  
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
