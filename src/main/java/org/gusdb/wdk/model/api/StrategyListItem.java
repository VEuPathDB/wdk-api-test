package org.gusdb.wdk.model.api;

import java.util.Date;

public class StrategyListItem {
  private long strategyId;
  private String description;
  private String name;
  private String displayName;
  private long latestStepId;
  private String recordClassNamePlural;
  private String signature;
  private Date lastModifiedTime;
  private String organization;
  private Boolean isPublic;
  private Boolean isSaved;
  private Boolean isDeleted;
  private Boolean isValid;
  private Integer estimatedSize;
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
  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  public long getLatestStepId() {
    return latestStepId;
  }
  public void setLatestStepId(long latestStepId) {
    this.latestStepId = latestStepId;
  }
  public String getRecordClassNamePlural() {
    return recordClassNamePlural;
  }
  public void setRecordClassNamePlural(String recordClassNamePlural) {
    this.recordClassNamePlural = recordClassNamePlural;
  }
  public String getSignature() {
    return signature;
  }
  public void setSignature(String signature) {
    this.signature = signature;
  }
  public Date getLastModifiedTime() {
    return lastModifiedTime;
  }
  public void setLastModifiedTime(Date lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
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
  
  
}
