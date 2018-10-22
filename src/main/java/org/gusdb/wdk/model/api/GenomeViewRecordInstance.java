package org.gusdb.wdk.model.api;

import java.util.List;

public class GenomeViewRecordInstance extends RecordInstance {

  private List<GenomeViewSequence> sequences;

  public List<GenomeViewSequence> getSequences() {
    return sequences;
  }

  public void setSequences(List<GenomeViewSequence> sequences) {
    this.sequences = sequences;
  }
  
  
}
