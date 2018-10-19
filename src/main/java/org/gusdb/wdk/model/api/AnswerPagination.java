package org.gusdb.wdk.model.api;

public class AnswerPagination {
  int offset;
  int numRecords;
  
  public int getOffset() {
    return offset;
  }
  public void setOffset(int offset) {
    this.offset = offset;
  }
  public int getNumRecords() {
    return numRecords;
  }
  public void setNumRecords(int numRecords) {
    this.numRecords = numRecords;
  }
}
