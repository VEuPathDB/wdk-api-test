package org.apidb.apicommon.model.api;

public class Target {
  private String id;
  private String type;

  public String getId() {
    return id;
  }

  public Target setId(String id) {
    this.id = id;
    return this;
  }

  public String getType() {
    return type;
  }

  public Target setType(String type) {
    this.type = type;
    return this;
  }
}
