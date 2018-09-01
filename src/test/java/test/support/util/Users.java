package test.support.util;

import test.support.Conf;

public class Users {
  public static final String BASE_PATH = Conf.SERVICE_PATH + "/users";
  public static final String BY_ID_PATH = BASE_PATH + "/{userId}";

  public static final Users INSTANCE = new Users();

  private Users() {}

  public static Users getInstance() {
    return INSTANCE;
  }
}
