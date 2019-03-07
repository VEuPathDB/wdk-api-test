package org.gusdb.wdk.model.api;

import java.util.Map;

public class StepDisplayPreferences {
  private String[] _columnSelection;
  private Map<String, SortSpec.SortDirection> _columnSorting;
  
  public String[] getColumnSelection() {
    return _columnSelection;
  }
  public void setColumnSelection(String[] columnSelection) {
    _columnSelection = columnSelection;
  }
  public Map<String, SortSpec.SortDirection> getColumnSorting() {
    return _columnSorting;
  }
  public void setColumnSorting(Map<String, SortSpec.SortDirection> columnSorting) {
    _columnSorting = columnSorting;
  }
  
}
