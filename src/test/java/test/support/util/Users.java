package test.support.util;

public class Users {
  public static final Users INSTANCE = new Users();
  private Users() {}

  public static Users getInstance() {
    return INSTANCE;
  }
}
