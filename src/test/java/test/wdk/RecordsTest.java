package test.wdk;

import org.junit.jupiter.api.Test;
import test.support.util.RequestFactory;

import static test.support.Conf.SERVICE_PATH;

public class RecordsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/records";

  private final RequestFactory req;

  RecordsTest(RequestFactory req) {
    this.req = req;
  }

  @Test
  void getRecordNames() {
    req.jsonSuccessRequest().when().get(BASE_PATH);
  }

  @Test
  void getRecordDetails() {
    req.jsonSuccessRequest().when().get(BASE_PATH + "?format=expanded");
  }
}
