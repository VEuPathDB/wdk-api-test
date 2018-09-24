package test.support.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.LoginRequest;
import org.gusdb.wdk.model.api.OAuthStateTokenResponse;
import test.service.OAuthTest;
import test.support.Conf;
import test.support.Header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static test.service.LoginTest.OAUTH_AUTHORIZE;
import static test.service.LoginTest.OAUTH_LOGIN;
import static test.support.Conf.SERVICE_PATH;

public class Auth {
  private static Auth instance;
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
        sessionToken = legacyLogin(Conf.EMAIL, Conf.PASSWORD, Conf.SITE_PATH)
            .getCookie(Conf.WDK_AUTH_COOKIE);
        break;

      case OAUTH:
        sessionToken = oAuthLogin(Conf.EMAIL, Conf.PASSWORD, Conf.SITE_PATH)
            .getCookie(Conf.WDK_AUTH_COOKIE);
        break;

      default:
        throw new RuntimeException(String.format("Auth type %s not supported.", Conf.AUTH_TYPE.name()));
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
    return RestAssured.given().cookie(Conf.WDK_AUTH_COOKIE, getSession());
  }

  public Response oAuthLogin(
      final String email,
      final String password,
      final String redirect
  ) {
    final String oauthState, redirectPath;
    final Response oauthCheckRes, loginRes, oauthStateRes;

    // Get State Token
    oauthStateRes = expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .cookie(Conf.JSESS_AUTH_COOKIE)
        .when()
        .get(OAuthTest.TOKEN_PATH);

    oauthState = oauthStateRes.as(OAuthStateTokenResponse.class)
        .getOauthStateToken();

    try {
      redirectPath = URLEncoder.encode(redirect, "utf-8");
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    // Get EuPathDB login cookie
    oauthCheckRes = given()
        .queryParams(
            "response_type", "code",
            "scope", "openid email",
            "state", oauthState,
            "client_id", "apiComponentSite",
            "redirect_uri", Conf.SITE_PATH + LOGIN_PATH + "?redirectUrl=" + redirectPath
        )
        .redirects()
        .follow(false)
        .when()
        .get(OAUTH_AUTHORIZE);

    // Submit login
    loginRes = given()
        .redirects()
        .follow(true)
        .formParams("username", email, "password", password)
        .cookie(
            Conf.EUPATH_AUTH_COOKIE,
            oauthCheckRes.cookie(Conf.EUPATH_AUTH_COOKIE)
        )
        .header(Header.REFERER, oauthCheckRes.header(Header.LOCATION))
        .when()
        .post(OAUTH_LOGIN);

    // Return to site to confirm oauth code and get WDK auth token
    return given()
        .redirects()
        .follow(false)
        .cookie(
            Conf.JSESS_AUTH_COOKIE,
            oauthStateRes.cookie(Conf.JSESS_AUTH_COOKIE)
        )
        .when()
        .get(loginRes.header(Header.LOCATION));
  }

  public Response legacyLogin(String email, String pass, String redirect) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .body(new LoginRequest(email, pass, redirect))
      .when()
        .post(LOGIN_PATH);
  }

  public static Auth getInstance() {
    if(instance == null) {
      instance = new Auth();
    }

    return instance;
  }
}
