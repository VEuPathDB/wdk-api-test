package org.gusdb.wdk.model.api;

public class AnalysisTypeSummary {
  private boolean hasParameters;
  private String displayName;
  private String releaseVersion;
  private String name;
  private String description;
  private String customThumbnail;
  private String shortDescription;

  public boolean isHasParameters() {
    return hasParameters;
  }

  public void setHasParameters(boolean hasParameters) {
    this.hasParameters = hasParameters;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getReleaseVersion() {
    return releaseVersion;
  }

  public void setReleaseVersion(String releaseVersion) {
    this.releaseVersion = releaseVersion;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCustomThumbnail() {
    return customThumbnail;
  }

  public void setCustomThumbnail(String customThumbnail) {
    this.customThumbnail = customThumbnail;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  @Override
  public String toString() {
    return "{" + "name='" + name + '\'' + '}';
  }
}
