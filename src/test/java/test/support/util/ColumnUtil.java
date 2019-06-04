package test.support.util;

public class ColumnUtil {
  public static final String
    SEGMENT      = "/columns",
    KEY          = "/{columnName}",
    BASE_URI     = SearchUtil.KEYED_URI + SEGMENT,
    KEYED_URI    = BASE_URI + KEY,
    EXPANDED_URI = BASE_URI + "?format=expanded";

  public static String[] allColumnNames(
    RequestFactory reqFac,
    String recordType,
    String question
  ) {
    return reqFac.jsonSuccessRequest()
      .when()
      .get(BASE_URI, recordType, question)
      .as(String[].class);
  }
}
