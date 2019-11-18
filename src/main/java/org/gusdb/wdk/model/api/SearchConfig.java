package org.gusdb.wdk.model.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchConfig {

  Map<String,String> parameters = new HashMap<String, String>();
  String legacyFilterName;
  Integer wdkWeight;
  List<FilterValueSpec> filters = new ArrayList<FilterValueSpec>();

  public ColumnFilterConfig columnFilters;

  public SearchConfig() {
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public SearchConfig setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }

  public String getLegacyFilterName() {
    return legacyFilterName;
  }
  public void setLegacyFilterName(String legacyFilterName) {
    this.legacyFilterName = legacyFilterName;
  }
  public Integer getWdkWeight() {
    return wdkWeight;
  }
  public void setWdkWeight(Integer wdk_weight) {
    this.wdkWeight = wdk_weight;
  }
  public List<FilterValueSpec> getFilters() {
    return filters;
  }
  public void setFilters(List<FilterValueSpec> filters) {
    this.filters = filters;
  }

}
