package test.wdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import test.support.Category;
import test.support.util.GuestRequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Records")
public class RecordsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/record-types";
  public static final String EXP_PATH = BASE_PATH + "?format=expanded";
  public static final String NAME_PATH = SERVICE_PATH + "/record-types/{recordClassName}";


  private final GuestRequestFactory req;

  RecordsTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getAllRecordNames() {
    req.jsonSuccessRequest().when().get(BASE_PATH);
  }

  @Test
  @DisplayName("GET " + EXP_PATH)
  void getAllRecordDetails() {
    req.jsonSuccessRequest().when().get(EXP_PATH);
  }
  
  @Test
  @DisplayName("GET popsetSetquence record type")
  @Tag (Category.PLASMO_TEST)
  void getPopsetRecordDetails() {
    req.jsonSuccessRequest().when().get(NAME_PATH, "popsetSequence");
  }


}
