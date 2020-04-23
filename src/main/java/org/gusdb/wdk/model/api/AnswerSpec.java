package org.gusdb.wdk.model.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerSpec {
  Map<String, String> parameters = new HashMap<>();
  String legacyFilterName;
  Integer wdk_weight;
  List<FilterValueSpec> filters = new ArrayList<>();
  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  public String getLegacyFilterName() {
    return legacyFilterName;
  }

  public void setLegacyFilterName(String legacyFilterName) {
    this.legacyFilterName = legacyFilterName;
  }

  public Integer getWdk_weight() {
    return wdk_weight;
  }

  public void setWdk_weight(Integer wdk_weight) {
    this.wdk_weight = wdk_weight;
  }

  public List<FilterValueSpec> getFilters() {
    return filters;
  }

  public void setFilters(List<FilterValueSpec> filters) {
    this.filters = filters;
  }
}
