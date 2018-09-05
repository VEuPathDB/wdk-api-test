package test.support.util;

import test.support.Conf;

public class Users {
  private static Users instance;
  private Users() {}

  public static final String BASE_PATH = Conf.SERVICE_PATH + "/users";
  public static final String BY_ID_PATH = BASE_PATH + "/{userId}";

  public static final Users INSTANCE = new Users();

  public static Users getInstance() {
    if (instance == null) {
      instance = new Users();
    }
    return instance;
  }
}
