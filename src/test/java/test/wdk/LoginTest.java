package test.wdk;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.LoginRequest;
import org.gusdb.wdk.model.api.OAuthStateTokenResponse;
import org.hamcrest.core.StringContains;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;
import test.support.Category;
import test.support.Conf;
import test.support.Credentials;
import test.support.Header;
import test.support.util.GuestRequestFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static test.support.Conf.*;
import static test.support.util.AuthUtil.LOGIN_PATH;

@DisplayName("Login")
public class LoginTest extends TestBase {
  public static final String OAUTH_AUTHORIZE = OAUTH_SERVICE + "/authorize";
  public static final String OAUTH_LOGIN     = OAUTH_SERVICE + "/login";

  private final GuestRequestFactory req;

  LoginTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @Tag(Category.AUTH_TEST)
  @DisplayName("User Login")
  void testLogin() throws UnsupportedEncodingException {
    // make sure user provided credentials; skip login test if not
    if (Conf.CREDENTIALS.length != 0) {
      switch (Conf.AUTH_TYPE) {
        case LEGACY:
          testLegacy();
          break;

        case OAUTH:
          testOAuth2();
          break;

        default:
          throw new JUnitException("Test not implemented for auth type " + Conf.AUTH_TYPE.name());
      }
    }
  }

  /**
   * Test Login using legacy authentication
   */
  private void testLegacy() {
    final Credentials creds = Conf.CREDENTIALS[0];
    req.jsonIoSuccessRequest(
        new LoginRequest(creds.getEmail(), creds.getPassword(), Conf.SITE_PATH))
        .cookie(WDK_AUTH_COOKIE)
        .when()
        .post(LOGIN_PATH);
  }

  private void testOAuth2() throws UnsupportedEncodingException {
    final String oauthState, redirectPath;
    final Response oauthCheckRes, loginRes, oauthStateRes;
    final Credentials creds = Conf.CREDENTIALS[0];

    // Get State Token
    oauthStateRes = req.jsonSuccessRequest()
        .cookie(JSESS_AUTH_COOKIE)
        .when()
        .get(OAuthTest.TOKEN_PATH);

    oauthState = oauthStateRes.as(OAuthStateTokenResponse.class)
        .getOauthStateToken();

    redirectPath = URLEncoder.encode(Conf.SITE_PATH, "utf-8");

    // Get EuPathDB login cookie
    oauthCheckRes = req.emptyRequest()
        .queryParams(
          "response_type", "code",
          "scope", "openid email",
          "state", oauthState,
          "client_id", "apiComponentSite",
          "redirect_uri", Conf.SITE_PATH + LOGIN_PATH + "?redirectUrl=" + redirectPath
        )
        .redirects()
        .follow(false)
        .expect()
        .statusCode(HttpStatus.SC_SEE_OTHER)
        .cookie(EUPATH_AUTH_COOKIE)
        .header(Header.LOCATION, not(new IsEmptyString()))
        .when()
        .get(OAUTH_AUTHORIZE);

    // Submit login
    loginRes = req.emptyRequest()
        .redirects()
        .follow(true)
        .formParams("username", creds.getEmail(), "password", creds.getPassword())
        .cookie(EUPATH_AUTH_COOKIE, oauthCheckRes.cookie(EUPATH_AUTH_COOKIE))
        .header(Header.REFERER, oauthCheckRes.header(Header.LOCATION))
        .expect()
        .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
        .header(Header.LOCATION, not(new IsEmptyString()))
        .when()
        .post(OAUTH_LOGIN);

    // Return to site to confirm oauth code and get WDK auth token
    req.emptyRequest()
        .redirects()
        .follow(false)
        .cookie(JSESS_AUTH_COOKIE, oauthStateRes.cookie(JSESS_AUTH_COOKIE))
        .expect()
        .statusCode(HttpStatus.SC_TEMPORARY_REDIRECT)
        .header(
          Header.LOCATION,
          allOf(
            not(new IsEmptyString()),
            not(new StringContains("login-error"))
          )
        )
        .cookie(WDK_AUTH_COOKIE)
        .when()
        .get(loginRes.header(Header.LOCATION));
  }
}
