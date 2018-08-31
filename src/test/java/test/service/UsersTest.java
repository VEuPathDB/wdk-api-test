package test.service;

import org.junit.jupiter.api.DisplayName;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Users")
public class UsersTest extends TestBase {

  public static final String BASE_PATH = SERVICE_PATH + "/users";
  public static final String BY_ID_PATH = BASE_PATH + "/{userId}";

  protected static final String DEFAULT_USER = "current";
}
