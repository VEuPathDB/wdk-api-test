package test.service;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import test.support.util.RequestFactory;

import static test.support.Conf.SERVICE_PATH;

public class ApiTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/api";
  public static final String KEYS_PATH = BASE_PATH + "/keys";

  private final RequestFactory req;

  public ApiTest(RequestFactory req) {
    this.req = req;
  }

  @Test
  void getApi() {
    req.request(HttpStatus.SC_OK, ContentType.TEXT)
        .when()
        .get(BASE_PATH);
  }

  @Test
  void getKeys() {
    req.jsonSuccessRequest().when().get(KEYS_PATH);
  }
}
