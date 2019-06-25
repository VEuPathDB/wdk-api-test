package org.gusdb.wdk.model.api;

// includes only one the single property we want to patch, lest we clobber other properties
public class StrategyPatchNameRequest {
  public StrategyPatchNameRequest(String name) {
    this.name = name;
  }

  public String name;
}
