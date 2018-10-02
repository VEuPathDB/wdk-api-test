package org.gusdb.wdk.model.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerSpec {
  String questionName;
  Map<String,String> parameters = new HashMap<String, String>();
  String legacyFilterName;
  Integer wdk_weight;
  List<FilterValueSpec> filters = new ArrayList<FilterValueSpec>();
  List<FilterValueSpec> viewFilters = new ArrayList<FilterValueSpec>();
  
  public AnswerSpec(String questionName) {
    this.questionName = questionName;
  }
  public String getQuestionName() {
    return questionName;
  }
  public void setQuestionName(String questionName) {
    this.questionName = questionName;
  }
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
  public List<FilterValueSpec> getViewFilters() {
    return viewFilters;
  }
  public void setViewFilters(List<FilterValueSpec> viewFilters) {
    this.viewFilters = viewFilters;
  }
  
  

}
