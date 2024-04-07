package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.junit.jupiter.api.DisplayName;

import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("Users")
public class UsersTest extends TestBase {

  public static final String USERS_BASE_PATH = SERVICE_PATH + "/users";
  public static final String USERS_BY_ID_PATH = USERS_BASE_PATH + "/{userId}";

  protected static final String DEFAULT_USER = "current";
  protected static final String INVALID_USER = "-1";

  protected final Session _guestSession;

  public UsersTest(SessionFactory sessionFactory) {
    _guestSession = sessionFactory.getCachedGuestSession();
  }
}
