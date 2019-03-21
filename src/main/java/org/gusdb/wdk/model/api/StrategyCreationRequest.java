package org.gusdb.wdk.model.api;

public class StrategyCreationRequest {
  String _description;
  String _name;
  String _savedName;
  Boolean _isSaved = false;
  Boolean _isPublic = false;
  StepTreeNode _stepTree;
  
  public StrategyCreationRequest(String name, StepTreeNode stepTree) {
    _name = name;
    _stepTree = stepTree;
  }
  
  public String getDescription() {
    return _description;
  }
  public void setDescription(String description) {
    _description = description;
  }
  public String getName() {
    return _name;
  }
  public void setName(String name) {
    _name = name;
  }
  public String getSavedName() {
    return _savedName;
  }
  public void setSavedName(String savedName) {
    _savedName = savedName;
  }
  public Boolean getIsSaved() {
    return _isSaved;
  }
  public void setIsSaved(Boolean isSaved) {
    _isSaved = isSaved;
  }
  public Boolean getIsPublic() {
    return _isPublic;
  }
  public void setIsPublic(Boolean isPublic) {
    _isPublic = isPublic;
  }
  public StepTreeNode getStepTree() {
    return _stepTree;
  }
  public void setStepTree(StepTreeNode stepTree) {
    _stepTree = stepTree;
  }

}

