package org.gusdb.wdk.model.api;

import java.util.List;

public class GenomeViewInstance  {

  private Boolean isDetail;
  private Integer maxLength;
  private List<GenomeViewSequence> sequences;

  public List<GenomeViewSequence> getSequences() {
    return sequences;
  }

  public void setSequences(List<GenomeViewSequence> sequences) {
    this.sequences = sequences;
  }

  public Boolean getIsDetail() {
    return isDetail;
  }

  public void setIsDetail(Boolean isDetail) {
    this.isDetail = isDetail;
  }

  public Integer getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(Integer maxLength) {
    this.maxLength = maxLength;
  }
  
  
}
