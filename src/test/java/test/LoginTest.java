package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Login API")
public class LoginTest extends TestBase {

  public static final String LOGIN_PATH = SERVICE_PATH + "/login";
  public static final String AUTH_COOKIE = "wdk_check_auth";

  @Test
  @DisplayName("User Login")
  public void testLogin() {
    login();
  }

  public static String login() {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .body(new LoginRequest(username, password, RestAssured.baseURI))
      .expect()
        .cookie(AUTH_COOKIE)
        .statusCode(HttpStatus.SC_OK)
//        .contentType(ContentType.JSON) // FIXME: API does not return content type JSON
      .when()
        .post(LOGIN_PATH)
        .getCookie(AUTH_COOKIE);
  }
}
