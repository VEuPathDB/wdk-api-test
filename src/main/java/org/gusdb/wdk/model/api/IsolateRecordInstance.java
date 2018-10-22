package org.gusdb.wdk.model.api;

import java.util.List;

public class IsolateRecordInstance extends RecordInstance {
  List<Isolate> isolates;

  public List<Isolate> getIsolates() {
    return isolates;
  }

  public void setIsolates(List<Isolate> isolates) {
    this.isolates = isolates;
  }
  
  
}
