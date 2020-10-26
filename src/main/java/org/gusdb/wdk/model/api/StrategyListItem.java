package org.gusdb.wdk.model.api;

import java.util.Date;


public class StrategyListItem {
  private long latestStepId;

  // Guaranteed present as of b47
  private Date lastViewed;
  private long rootStepId;
  private String signature;
  private String author;
  private String releaseVersion;
  private Boolean isValid;
  private String description;
  private int leafAndTransformStepCount;
  private Boolean isDeleted;
  private Integer estimatedSize;
  private Boolean isSaved;
  private String organization;
  private String name;
  private String recordClassName;
  private Date createdTime;
  private Boolean isPublic;
  private Boolean isExample;
  private long strategyId;
  private Date lastModified;
  private String nameOfFirstStep;

  public long getStrategyId() {
    return strategyId;
  }
  public void setStrategyId(long strategyId) {
    this.strategyId = strategyId;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String desc) { this.description = desc; }

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

  public long getLatestStepId() {
    return latestStepId;
  }
  public void setLatestStepId(long stepId) { this.latestStepId = stepId; }

  public String getRecordClassName() {
    return recordClassName;
  }
  public void setRecordClassName(String rc) { this.recordClassName = rc; }

  public String getSignature() {
    return signature;
  }
  public void setSignature(String signature) {
    this.signature = signature;
  }

  public Date getLastModified() {
    return lastModified;
  }
  public void setLastModified(Date lastMod) { this.lastModified = lastMod; }

  public String getOrganization() {
    return organization;
  }
  public void setOrganization(String org) { this.organization = org; }

  public Boolean getIsPublic() {
    return isPublic;
  }
  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }

  public Boolean getIsExample() {
    return isExample;
  }
  public void setIsExample(Boolean isExample) {
    this.isExample = isExample;
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
  public void setEstimatedSize(Integer eSize) { this.estimatedSize = eSize; }

  public Date getLastViewed() { return lastViewed; }
  public void setLastViewed(Date lastViewed) { this.lastViewed = lastViewed; }

  // a simple toString method for use in reporting info about failed strats
  public String toString() {
    return "Strategy: " + getName() + " (signature " + getSignature() +")";
  }

  public long getRootStepId() {
    return rootStepId;
  }

  public void setRootStepId(long rootStepId) {
    this.rootStepId = rootStepId;
  }

  public String getReleaseVersion() {
    return releaseVersion;
  }

  public void setReleaseVersion(String releaseVersion) {
    this.releaseVersion = releaseVersion;
  }

  public int getLeafAndTransformStepCount() {
    return leafAndTransformStepCount;
  }

  public void setLeafAndTransformStepCount(int leafAndTransformStepCount) {
    this.leafAndTransformStepCount = leafAndTransformStepCount;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  public String getNameOfFirstStep() {
    return nameOfFirstStep;
  }

  public void setNameOfFirstStep(String nameOfFirstStep) {
    this.nameOfFirstStep = nameOfFirstStep;
  }
}
