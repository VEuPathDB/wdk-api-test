package org.gusdb.wdk.model.api;

import java.util.List;

public class GenomeViewSequence {
  private  String sourceId;
  private  List<GenomeViewRegion> regions;
  private  List<GenomeViewFeature> features;
  private long length;
  private double percentLength;
  private String chromosome;
  private String organism;

public String getSourceId() {
    return sourceId;
  }
  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }
  public List<GenomeViewRegion> getRegions() {
    return regions;
  }
  public void setRegions(List<GenomeViewRegion> regions) {
    this.regions = regions;
  }
  public List<GenomeViewFeature> getFeatures() {
    return features;
  }
  public void setFeatures(List<GenomeViewFeature> features) {
    this.features = features;
  }
  public long getLength() {
    return length;
  }
  public void setLength(long length) {
    this.length = length;
  }
  public double getPercentLength() {
    return percentLength;
  }
  public void setPercentLength(double percentLength) {
    this.percentLength = percentLength;
  }
  public String getChromosome() {
    return chromosome;
  }
  public void setChromosome(String chromosome) {
    this.chromosome = chromosome;
  }
  public String getOrganism() {
    return organism;
  }
  public void setOrganism(String organism) {
    this.organism = organism;
  }

}
