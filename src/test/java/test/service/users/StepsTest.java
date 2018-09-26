package test.service.users;

import org.junit.jupiter.api.DisplayName;
import test.service.UsersTest;
import test.support.util.AuthUtil;
import test.support.util.AuthenticatedRequestFactory;

@DisplayName("Steps")
public class StepsTest extends UsersTest {
  public static final String BASE_PATH = UsersTest.BY_ID_PATH + "/steps";
  public static final String BY_ID_PATH = BASE_PATH + "/{stepId}";

  protected final AuthUtil auth;

  public StepsTest(AuthUtil auth, AuthenticatedRequestFactory req) {
    super(req);
    this.auth = auth;
  }
}
