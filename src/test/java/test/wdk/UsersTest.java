package test.wdk;

import org.junit.jupiter.api.DisplayName;

import test.support.util.AuthenticatedRequestFactory;

@DisplayName("Users")
public class UsersTest extends TestBase {

  public static final String BASE_PATH = "/users";
  public static final String BY_ID_PATH = BASE_PATH + "/{userId}";

  protected static final String DEFAULT_USER = "current";
  protected static final String INVALID_USER = "-1";

  private final AuthenticatedRequestFactory _authenticatedRequestFactory;

  public UsersTest(AuthenticatedRequestFactory req) {
    _authenticatedRequestFactory = req;
  }
  
  protected AuthenticatedRequestFactory getAuthenticatedRequestFactory() {
    return _authenticatedRequestFactory;
  }
}
