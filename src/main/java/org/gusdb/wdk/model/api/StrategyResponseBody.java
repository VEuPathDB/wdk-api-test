package org.gusdb.wdk.model.api;

import java.util.Map;

public class StrategyResponseBody extends StrategyListItem {
  private Map<Long, StepResponseBody> _steps;
  private StepTreeNode _stepTree;
  
  public Map<Long, StepResponseBody> getSteps() {
    return _steps;
  }
  public void setSteps(Map<Long, StepResponseBody> steps) {
    _steps = steps;
  }
  public StepTreeNode getStepTree() {
    return _stepTree;
  }
  public void setStepTree(StepTreeNode stepTree) {
    _stepTree = stepTree;
  }
  
  

}
