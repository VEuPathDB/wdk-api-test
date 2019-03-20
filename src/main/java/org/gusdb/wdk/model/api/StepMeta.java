package org.gusdb.wdk.model.api;

/**
 * Base properties of a step (excluding SearchConfig)
 * @author Steve
 *
 */
public class StepMeta {
  String customName;
  String collapsedName;
  Boolean isCollapsed;
  StepDisplayPreferences displayPreferences;
  
  public StepMeta() {}
  
  public StepDisplayPreferences getDisplayPreferences() {
    return displayPreferences;
  }

  public void setDisplayPreferences(StepDisplayPreferences displayPreferences) {
    this.displayPreferences = displayPreferences;
  }

  public String getCustomName() {
    return customName;
  }
  public void setCustomName(String customName) {
    this.customName = customName;
  }
  public String getCollapsedName() {
    return collapsedName;
  }
  public void setCollapsedName(String collapsedName) {
    this.collapsedName = collapsedName;
  }
  public Boolean getIsCollapsed() {
    return isCollapsed;
  }
  public void setIsCollapsed(Boolean isCollapsed) {
    this.isCollapsed = isCollapsed;
  }
  
}
