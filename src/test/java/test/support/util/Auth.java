package test.support.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.gusdb.wdk.model.api.LoginRequest;
import test.support.Conf;

import static test.support.Conf.SERVICE_PATH;

public class Auth {
  public static final Auth INSTANCE = new Auth();
  private Auth() {}

  public static final String LOGIN_PATH = SERVICE_PATH + "/login";

  private String sessionToken;

  public enum Type {
    OAUTH,
    LEGACY
  }

  /**
   * Create a new session.
   */
  public void newSession() {
    switch (Conf.AUTH_TYPE) {
      case LEGACY:
        sessionToken = legacyLogin(Conf.USERNAME, Conf.PASSWORD, Conf.SITE_PATH)
            .getCookie(Conf.AUTH_COOKIE);
        break;

      default:
        throw new RuntimeException("Only legacy login currently supported");
    }
  }

  /**
   * Get session token for current session.  If no current session exists, one
   * will be created.
   */
  public String getSession() {
    if(sessionToken == null) {
      newSession();
    }
    return sessionToken;
  }

  public RequestSpecification prepRequest() {
    return RestAssured.given().cookie(Conf.AUTH_COOKIE, getSession());
  }

  public Response oAuthLogin() {
    // TODO
    return null;
  }

  public Response legacyLogin(String email, String pass, String redirect) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .body(new LoginRequest(email, pass, redirect))
      .when()
        .post(LOGIN_PATH);
  }

  public static Auth getInstance() {
    return INSTANCE;
  }
}
