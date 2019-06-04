package test.support.util;

import org.gusdb.wdk.model.api.Search;

public class SearchUtil {
  public static final String
    BASE_URI  = RecordTypeUtil.KEYED_URI + "/searches",
    KEYED_URI = BASE_URI + "/{searchName}",
    EXPANDED  = BASE_URI + "?format=expanded";

  public static Search[] searchList(RequestFactory rFac, String recType) {
    return rFac.jsonSuccessRequest()
      .when()
      .get(EXPANDED, recType)
      .as(Search[].class);
  }

  /**
   * Retrieves a single Search by name.
   *
   * @param rFac
   *   pre-configured request factory
   * @param recType
   *   record type this search will appear on
   * @param search
   *   name of the search to retrieve
   *
   * @return a search instance matching the given record type name and search
   * name
   */
  public static Search getSearch(
    final RequestFactory rFac,
    final String recType,
    final String search
  ) {
    return rFac.jsonSuccessRequest()
      .when()
      .get(KEYED_URI, recType, search)
      .as(Search.class);
  }
}
