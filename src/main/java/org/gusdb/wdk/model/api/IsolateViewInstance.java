package org.gusdb.wdk.model.api;

import java.util.List;

public class IsolateViewInstance {
  List<Isolate> isolates;
  Integer maxLength;

  public List<Isolate> getIsolates() {
    return isolates;
  }

  public void setIsolates(List<Isolate> isolates) {
    this.isolates = isolates;
  }

  public void setMaxLength(Integer i) {
    maxLength = i;
  }
  
  
}
