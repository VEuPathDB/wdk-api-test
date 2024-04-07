package test.support.util;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static test.support.Conf.SERVICE_PATH;
import static test.wdk.LoginTest.OAUTH_AUTHORIZE;
import static test.wdk.LoginTest.OAUTH_LOGIN;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.LoginRequest;
import org.gusdb.wdk.model.api.OAuthStateTokenResponse;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Conf;
import test.support.Credentials;
import test.support.Header;
import test.wdk.OAuthTest;

/**
 * Utility for performing common site session tasks.
 */
public class SessionFactory {

  private static SessionFactory instance;

  private static final String ERR_UNSUPPORTED_AUTH = "Auth type %s is not supported.";

  public static final String LOGIN_PATH = SERVICE_PATH + "/login";

  public static final String CURRENT_USER_PATH = SERVICE_PATH + "/users/current";

  private final Map<Credentials, Session> cachedAuthenticatedSessions;
  private final Session cachedGuestSession;

  public enum Type {
    OAUTH,
    LEGACY; // direct username/password
  }

  private SessionFactory() {

    // Synchronized because tests can be run in parallel
    cachedAuthenticatedSessions = Collections.synchronizedMap(new HashMap<>());

    // Pre-load a guest token to be used for guest requests
    cachedGuestSession = getNewGuestSession();
  }

  public Session getCachedGuestSession() {
    return cachedGuestSession;
  }

  public Session getNewGuestSession() {
    return new Session(
      new RequestFactory().emptyRequest()
        .get(Conf.SITE_PATH + CURRENT_USER_PATH)
        .getCookie(Conf.WDK_AUTH_COOKIE));
  }

  public Session getAuthenticatedSession() {
    Credentials[] creds = Conf.CREDENTIALS;
    if (creds.length == 0) {
      throw new RuntimeException("No credentials submitted; cannot create authenticated session.");
    }
    return getAuthenticatedSession(creds[0]);
  }

  /**
   * Create and store a new login session for the given credentials.
   *
   * If a session already exists for the given user it will be overwritten with
   * the new session.
   *
   * @param credentials Site login credentials
   */
  public Session getAuthenticatedSession(final Credentials credentials) {
    return cachedAuthenticatedSessions.computeIfAbsent(credentials, creds -> {
      switch (Conf.AUTH_TYPE) {
        case LEGACY:
          return new Session(legacyLogin(creds, Conf.SITE_PATH).getCookie(Conf.WDK_AUTH_COOKIE));
        case OAUTH:
          return new Session(oAuthLogin(creds, Conf.SITE_PATH).getCookie(Conf.WDK_AUTH_COOKIE));
        default:
          throw new RuntimeException(String.format(ERR_UNSUPPORTED_AUTH, Conf.AUTH_TYPE.name()));
      }
    });
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
  private Response oAuthLogin(
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

    redirectPath = URLEncoder.encode(redirect, StandardCharsets.UTF_8);

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
  private Response legacyLogin(final Credentials creds, final String redirect) {
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
  public static SessionFactory getInstance() {
    if(instance == null) {
      instance = new SessionFactory();
    }

    return instance;
  }
}
