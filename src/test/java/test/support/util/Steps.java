package test.support.util;

public class Steps {

  public static final String BASE_PATH = Users.BY_ID_PATH + "/steps";
  public static final String BY_ID_PATH = BASE_PATH + "/{stepId}";

  private static final Steps INSTANCE = new Steps();

  private Steps() {}

  /**
   * TODO: This should be boilerplate to create a specific step.
   *
   * @return created step id
   */
  public long createStep() {
    // FIXME
    return 110719150L;
  }

  public static Steps getInstance() {
    return INSTANCE;
  }
}
