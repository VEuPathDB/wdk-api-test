package org.gusdb.wdk.model.api;


public class AnswerDetails {
  
  static final Integer ALL_RECORDS = -1;

  private int _offset = 0; // default start at first record
  private int _numRecords = ALL_RECORDS; // default to all records
  
  public int getOffset() {
    return _offset;
  }
  public void setOffset(int offset) {
    _offset = offset;
  }
  public int getNumRecords() {
    return _numRecords;
  }
  public void setNumRecords(int numRecords) {
    _numRecords = numRecords;
  }
 
  // TODO: fill in all the rest
  
  /*  THE BACKEND CLASS THIS IS BASED ON...
public class AnswerDetails {

  static final Integer ALL_RECORDS = -1;

  // use factory method to construct from JSON
  AnswerDetails() {}

  // all fields are private
  private int _offset = 0; // default start at first record
  private int _numRecords = ALL_RECORDS; // default to all records
  private Map<String, AttributeField> _attributes;
  private Map<String, TableField> _tables;
  private List<AttributeFieldSortSpec> _sorting;
  private ContentDisposition _contentDisposition = ContentDisposition.INLINE;

  // all getters are public
  public int getOffset() { return _offset; }
  public int getNumRecords() {  return _numRecords; }
  public Map<String, AttributeField> getAttributes() { return _attributes; }
  public Map<String, TableField> getTables() { return _tables; }
  public List<AttributeFieldSortSpec> getSorting() { return _sorting; }
  public ContentDisposition getContentDisposition() { return _contentDisposition; }

  // all setters are package-private, available only to factory
  void setOffset(int offset) { _offset = offset; }
  void setNumRecords(int numRecords) { _numRecords = numRecords; }
  void setAttributes(Map<String, AttributeField> attributes) { _attributes = attributes; }
  void setTables(Map<String, TableField> tables) { _tables = tables; }
  void setSorting(List<AttributeFieldSortSpec> sorting) { _sorting = sorting; }
  void setContentDisposition(ContentDisposition contentDisposition) { _contentDisposition = contentDisposition; }

}

   */

}
