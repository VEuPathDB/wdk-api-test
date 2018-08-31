package test.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;
import test.support.Conf;

import static test.support.util.Auth.LOGIN_PATH;
import static test.support.Conf.AUTH_COOKIE;

@DisplayName("Login")
public class LoginTest extends TestBase {

  @Test
  @DisplayName("User Login")
  void testLogin() {
    switch (Conf.AUTH_TYPE) {
      case LEGACY:
        testLegacy();
        break;

      default:
        throw new JUnitException("Test not implemented for auth type " + Conf.AUTH_TYPE.name());
    }
  }

  /**
   * Test Login using legacy authentication
   */
  private void testLegacy() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(new LoginRequest(Conf.USERNAME, Conf.PASSWORD, Conf.SITE_PATH))
      .expect()
        .cookie(AUTH_COOKIE)
        .statusCode(HttpStatus.SC_OK)
    //        .contentType(ContentType.JSON) // FIXME: API does not return content type JSON
      .when()
        .post(LOGIN_PATH);
  }
}
