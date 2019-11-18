package test.support.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.LoginRequest;
import org.gusdb.wdk.model.api.OAuthStateTokenResponse;
import test.wdk.OAuthTest;
import test.support.Conf;
import test.support.Credentials;
import test.support.Header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static test.wdk.LoginTest.OAUTH_AUTHORIZE;
import static test.wdk.LoginTest.OAUTH_LOGIN;

/**
 * Utility for performing common site session tasks.
 */
public class AuthUtil {
  private static AuthUtil instance;

  private static final String ERR_UNSUPPORTED_AUTH = "Auth type %s is not supported.";

  public static final String LOGIN_PATH = "/login";

  private Map < Credentials, String > sessionTokens;

  public enum Type {
    OAUTH,
    LEGACY
  }

  private AuthUtil() {
    // Synchronized because tests can be run in parallel.
    sessionTokens = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * Create and store a new login session for the given credentials.
   *
   * If a session already exists for the given user it will be overwritten with
   * the new session.
   *
   * @param creds Site login credentials
   */
  public void newSession(final Credentials creds) {
    switch (Conf.AUTH_TYPE) {
      case LEGACY:
        sessionTokens.put(creds, legacyLogin(creds, Conf.SITE_PATH)
            .getCookie(Conf.WDK_AUTH_COOKIE));
        break;
      case OAUTH:
        sessionTokens.put(creds, oAuthLogin(creds, Conf.SITE_PATH)
            .getCookie(Conf.WDK_AUTH_COOKIE));
        break;
      default:
        throw new RuntimeException(String.format(ERR_UNSUPPORTED_AUTH,
            Conf.AUTH_TYPE.name()));
    }
  }

  /**
   * Get the session token for the given set of credentials.
   *
   * If no current session exists for the given credentials, one will be
   * created.
   *
   * @param creds Site login credentials
   *
   * @return session token for the given credentials.
   */
  public String getSession(final Credentials creds) {
    if(!sessionTokens.containsKey(creds))
      newSession(creds);
    return sessionTokens.get(creds);
  }

  /**
   * Prepare an authenticated request for the given user credentials.
   *
   * @param creds Site login credentials
   *
   * @return A RestAssured request which includes the necessary setup to perform
   *         an authenticated request.
   */
  public RequestSpecification prepRequest(final Credentials creds) {
    return RestAssured.given().cookie(Conf.WDK_AUTH_COOKIE,
        getSession(creds));
  }

  /**
   * Prepare an authenticated request for the first known set of  user
   * credentials.
   *
   * @return A RestAssured request which includes the necessary setup to perform
   *         an authenticated request.
   */
  public RequestSpecification prepRequest() {
    return RestAssured.given().cookie(Conf.WDK_AUTH_COOKIE,
        getSession(Conf.CREDENTIALS[0]));
  }

  /**
   * Perform the login steps necessary to create a session for sites using OAuth
   * for login handling.
   *
   * @param creds Site login credentials
   * @param redirect Successful login redirect URL
   *
   * @return Response containing the WDK auth token.
   */
  public Response oAuthLogin(
      final Credentials creds,
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
        .formParams("username", creds.getEmail(), "password", creds.getPassword())
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

  /**
   * Perform the login steps necessary to create a session on sites using the
   * legacy login system.
   *
   * @param creds Site login credentials
   * @param redirect Successful login redirect url
   *
   * @return Response containing the WDK auth token.
   */
  public Response legacyLogin(final Credentials creds, final String redirect) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .body(new LoginRequest(creds.getEmail(), creds.getPassword(), redirect))
      .when()
        .post(LOGIN_PATH);
  }

  /**
   * Static singleton accessor.
   *
   * @return AuthUtil singleton instance.
   */
  public static AuthUtil getInstance() {
    if(instance == null) {
      instance = new AuthUtil();
    }

    return instance;
  }
}
