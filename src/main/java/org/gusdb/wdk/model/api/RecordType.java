package org.gusdb.wdk.model.api;

import util.Json;

public class RecordType {
  public String nativeDisplayName;
  public SearchData[] searches;
  public String urlSegment;
  public Boolean useBasket;
  public String nativeShortDisplayName;
  public Format[] formats;
  public Boolean hasAllRecordsQuery;
  public String displayName;
  public String nativeShortDisplayNamePlural;
  public String fullName;
  public String description;
  public String shortDisplayName;
  public String shortDisplayNamePlural;
  public Table[] tables;
  public String nativeDisplayNamePlural;
  public String displayNamePlural;
  public String recordIdAttributeName;
  public Attribute[] attributes;
  public String[] primaryKeyColumnRefs;

  @Override
  public String toString() {
    return Json.stringify(Json.object()
      .put("urlSegment", urlSegment)
      .put("displayName", displayName));
  }
}
