package test.wdk;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.support.util.GuestRequestFactory;
import static test.support.Conf.SERVICE_PATH;

@DisplayName("API")
public class ApiTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/api";
  public static final String KEYS_PATH = BASE_PATH + "/keys";

  private final GuestRequestFactory req;

  public ApiTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getApi() {
    req.successRequest(ContentType.TEXT).when().get(BASE_PATH);
  }

  @Test
  @DisplayName("GET " + KEYS_PATH)
  void getKeys() {
    req.jsonSuccessRequest().when().get(KEYS_PATH);
  }
}
