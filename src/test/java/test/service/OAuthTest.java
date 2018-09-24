package test.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("OAuth")
public class OAuthTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/oauth";
  public static final String TOKEN_PATH = BASE_PATH + "/state-token";

  @Test
  @DisplayName("State Token")
  void getStateToken() {
    RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(TOKEN_PATH);
  }
}
