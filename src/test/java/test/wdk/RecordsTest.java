package test.wdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.support.util.GuestRequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Records")
public class RecordsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/records";
  public static final String EXP_PATH = BASE_PATH + "?format=expanded";

  private final GuestRequestFactory req;

  RecordsTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getRecordNames() {
    req.jsonSuccessRequest().when().get(BASE_PATH);
  }

  @Test
  @DisplayName("GET " + EXP_PATH)
  void getRecordDetails() {
    req.jsonSuccessRequest().when().get(EXP_PATH);
  }
}
