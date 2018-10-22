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

}
