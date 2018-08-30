package test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Users API")
public class UsersTest extends TestBase {

  public static final String USERS_PATH = SERVICE_PATH + "/users";
  public static final String USER_BY_ID_PATH = USERS_PATH + "/{userId}";

  protected static final String DEFAULT_USER = "current";

  protected static String authCookie;

  /**
   * Prepare an authenticated request.
   *
   * @return Request Specification with the necessary properties for making an
   * authenticated request.
   */
  public static RequestSpecification prepAuthRequest() {
    return RestAssured.given().cookie(LoginTest.AUTH_COOKIE, authCookie);
  }


  @BeforeAll
  public static void login() {
    authCookie = LoginTest.login();
  }
}
