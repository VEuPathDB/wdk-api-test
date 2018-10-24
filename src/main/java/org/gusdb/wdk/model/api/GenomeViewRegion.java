package org.gusdb.wdk.model.api;

import java.util.List;

public class GenomeViewRegion {
  private  boolean forward;
  private  List<GenomeViewFeature> features;
  private double percentLength;
  private double percentStart;
  
  public boolean getIsForward() {
    return forward;
  }
  public void setIsForward(boolean forward) {
    this.forward = forward;
  }
  public List<GenomeViewFeature> getFeatures() {
    return features;
  }
  public void setFeatures(List<GenomeViewFeature> features) {
    this.features = features;
  }
  public double getPercentLength() {
    return percentLength;
  }
  public void setPercentLength(double percentLength) {
    this.percentLength = percentLength;
  }
  public double getPercentStart() {
    return percentStart;
  }
  public void setPercentStart(double percentStart) {
    this.percentStart = percentStart;
  }
  
  

}
