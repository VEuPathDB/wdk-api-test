package test.support.util;

import org.gusdb.wdk.model.api.RecordType;

import static test.support.Conf.SERVICE_PATH;

public class RecordTypeUtil {
  public static final String
    BASE_URI  = SERVICE_PATH + "/record-types",
    KEYED_URI = BASE_URI + "/{recordType}",
    EXPANDED  = BASE_URI + "?format=expanded";

  public static String[] getRecordTypeNames(RequestFactory rFac) {
    return rFac.jsonSuccessRequest()
      .when()
      .get(BASE_URI)
      .as(String[].class);
  }

  public static RecordType[] recordTypes(RequestFactory rFac) {
    return rFac.jsonSuccessRequest()
      .when()
      .get(EXPANDED)
      .as(RecordType[].class);
  }

  public static RecordType getRecordType(
    final RequestFactory rFac,
    final String name
  ) {
    return rFac.jsonSuccessRequest()
      .when()
      .get(KEYED_URI, name)
      .as(RecordType.class);
  }
}
