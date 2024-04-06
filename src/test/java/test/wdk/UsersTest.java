package test.wdk;

import org.junit.jupiter.api.DisplayName;
import test.support.util.AuthenticatedRequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Users")
public class UsersTest extends TestBase {

  public static final String USERS_BASE_PATH = SERVICE_PATH + "/users";
  public static final String USERS_BY_ID_PATH = USERS_BASE_PATH + "/{userId}";

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
