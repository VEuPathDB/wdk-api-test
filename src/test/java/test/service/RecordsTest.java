package test.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static test.support.Conf.SERVICE_PATH;

public class RecordsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/records";

  @Test
  void getRecordNames() {
    RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .when()
        .get(BASE_PATH);
  }

  @Test
  void getRecordDetails() {
    RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .when()
        .get(BASE_PATH + "?format=expanded");
  }
}
