package test.wdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.support.util.GuestRequestFactory;
import test.support.util.RequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Records")
public class RecordsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/record-types";
  public static final String EXP_PATH = BASE_PATH + "?format=expanded";

  private final GuestRequestFactory req;

  RecordsTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getRecordNames() {
    getAllRecordNames(req);
  }

  @Test
  @DisplayName("GET " + EXP_PATH)
  void getRecordDetails() {
    req.jsonSuccessRequest().when().get(EXP_PATH);
  }

  public static String[] getAllRecordNames(RequestFactory req) {
    return req.jsonSuccessRequest().when().get(BASE_PATH).as(String[].class);
  }
}
