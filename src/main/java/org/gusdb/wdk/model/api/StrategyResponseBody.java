package org.gusdb.wdk.model.api;

import java.util.List;

public class StrategyResponseBody extends StrategyListItem {
  private List<Step> _steps;
  private StepTreeNode _stepTree;
  
  public List<Step> getSteps() {
    return _steps;
  }
  public void setSteps(List<Step> steps) {
    _steps = steps;
  }
  public StepTreeNode getStepTree() {
    return _stepTree;
  }
  public void setStepTree(StepTreeNode stepTree) {
    _stepTree = stepTree;
  }
  
  

}
