package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import util.Json;

public class SearchData {
  public String summary;
  public String urlSegment;
  public Attribute[] dynamicAttributes;
  public SortSpec[] defaultSorting;
  public String displayName;
  public String fullName;
  public String description;
  public Group[] groups;
  public String[] defaultAttributes;
  public ArrayNode filters; // Not sure what this array looks like
  public String shortDisplayName;
  public String outputRecordClassName;
  public ArrayNode stepAnalysisPlugins; // not sure what this array looks like
  public String defaultSummaryView;
  public ObjectNode properties;
  public SummaryViewPlugin[] summaryViewPlugins;
  public String newBuild;
  public String[] allowedPrimaryInputRecordClassNames;
  public String[] allowedSecondaryInputRecordClassNames;
  public JsonNode parameters;

  @Override
  public String toString() {
    return Json.stringify(Json.object()
      .put("urlSegment", urlSegment)
      .put("displayName", displayName));
  }
}
