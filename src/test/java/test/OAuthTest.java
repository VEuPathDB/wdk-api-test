package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OAuth")
public class OAuthTest extends TestBase {
  public static final String OAUTH_PATH = SERVICE_PATH + "/oauth";

  @Test
  @DisplayName("State Token")
  void getStateToken() {
    RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(OAUTH_PATH + "/state-token");
  }
}
