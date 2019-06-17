package org.gusdb.wdk.model.api.record.search.reporter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collection;

public class JsonReportRecord {
  private ObjectNode tables; // Not sure what this is
  private String displayName;
  private String recordClassName;
  private RecordAttributes attributes;
  private Collection<PrimaryKey> id;
  private Collection<JsonNode> tableErrors;

  public ObjectNode getTables() {
    return tables;
  }

  public void setTables(ObjectNode tables) {
    this.tables = tables;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getRecordClassName() {
    return recordClassName;
  }

  public void setRecordClassName(String recordClassName) {
    this.recordClassName = recordClassName;
  }

  public RecordAttributes getAttributes() {
    return attributes;
  }

  public void setAttributes(RecordAttributes attributes) {
    this.attributes = attributes;
  }

  public Collection<PrimaryKey> getId() {
    return id;
  }

  public void setId(Collection<PrimaryKey> id) {
    this.id = id;
  }

  public Collection<JsonNode> getTableErrors() {
    return tableErrors;
  }

  public void setTableErrors(Collection<JsonNode> tableErrors) {
    this.tableErrors = tableErrors;
  }
}
