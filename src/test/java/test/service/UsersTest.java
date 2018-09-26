package test.service;

import org.junit.jupiter.api.DisplayName;
import test.support.util.AuthenticatedRequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Users")
public class UsersTest extends TestBase {

  public static final String BASE_PATH = SERVICE_PATH + "/users";
  public static final String BY_ID_PATH = BASE_PATH + "/{userId}";

  protected static final String DEFAULT_USER = "current";
  protected static final String INVALID_USER = "-1";

  protected final AuthenticatedRequestFactory req;

  public UsersTest(AuthenticatedRequestFactory req) {
    this.req = req;
  }
}
