package org.gusdb.wdk.model.api;

public class GenomeViewFeature {

  private String sourceId;
  private boolean forward;
  private String sequenceId;
  private long start;
  private long end;
  private double percentStart;
  private double percentLength;
  private String context;
  private String description;
  
  public long getStart() {
    return start;
  }
  public void setStart(long start) {
    this.start = start;
  }
  public long getEnd() {
    return end;
  }
  public void setEnd(long end) {
    this.end = end;
  }
  public double getPercentStart() {
    return percentStart;
  }
  public void setPercentStart(double percentStart) {
    this.percentStart = percentStart;
  }
  public double getPercentLength() {
    return percentLength;
  }
  public void setPercentLength(double percentLength) {
    this.percentLength = percentLength;
  }
  public String getContext() {
    return context;
  }
  public void setContext(String context) {
    this.context = context;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getSourceId() {
    return sourceId;
  }
  public boolean isForward() {
    return forward;
  }
  public String getSequenceId() {
    return sequenceId;
  }

  
}
