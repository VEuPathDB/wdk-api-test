package test.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static test.support.Conf.SERVICE_PATH;

public class ApiTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/api";
  public static final String KEYS_PATH = BASE_PATH + "/keys";

  @Test
  void getKeys() {
    RestAssured.expect()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.SC_OK)
        .when()
        .get(KEYS_PATH);
  }
}
