package org.gusdb.wdk.model.api;

import java.util.Date;

public class StrategyListItem {
  private long strategyId;
  private String description;
  private String name;
  private String author;
  private long rootStepId;
  private String recordClassName;
  private String signature;
  private Date lastModified;
  private String organization;
  private Boolean isPublic;
  private Boolean isSaved;
  private Boolean isDeleted;
  private Boolean isValid;
  private Integer estimatedSize;
  private Integer leafAndTransformStepCount;
  private String  nameOfFirstStep;

  public long getStrategyId() {
    return strategyId;
  }

  public void setStrategyId(long strategyId) {
    this.strategyId = strategyId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public long getRootStepId() {
    return rootStepId;
  }

  public void setRootStepId(long rootStepId) {
    this.rootStepId = rootStepId;
  }

  public String getRecordClassName() {
    return recordClassName;
  }

  public void setRecordClassName(String recordClassName) {
    this.recordClassName = recordClassName;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public Boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }

  public Boolean getIsSaved() {
    return isSaved;
  }

  public void setIsSaved(Boolean isSaved) {
    this.isSaved = isSaved;
  }

  public Boolean getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public Boolean getIsValid() {
    return isValid;
  }

  public void setIsValid(Boolean isValid) {
    this.isValid = isValid;
  }

  public Integer getEstimatedSize() {
    return estimatedSize;
  }

  public void setEstimatedSize(Integer estimatedSize) {
    this.estimatedSize = estimatedSize;
  }

  public Integer getLeafAndTransformStepCount() {
    return leafAndTransformStepCount;
  }

  public void setLeafAndTransformStepCount(Integer leafAndTransformStepCount) {
    this.leafAndTransformStepCount = leafAndTransformStepCount;
  }

  public String getNameOfFirstStep() {
    return nameOfFirstStep;
  }

  public void setNameOfFirstStep(String nameOfFirstStep) {
    this.nameOfFirstStep = nameOfFirstStep;
  }


  // a simple toString method for use in reporting info about failed strats
  @Override
  public String toString() {
    return "Strategy: " + getName() + " (signature " + getSignature() + ")";
  }
}
