package test.service.users;

import org.junit.jupiter.api.DisplayName;
import test.service.UsersTest;
import test.support.util.Auth;

@DisplayName("Steps")
public class StepsTest extends UsersTest {
  public static final String BASE_PATH = UsersTest.BY_ID_PATH + "/steps";
  public static final String BY_ID_PATH = BASE_PATH + "/{stepId}";

  protected final Auth auth;

  public StepsTest(Auth auth) {
    this.auth = auth;
  }
}
