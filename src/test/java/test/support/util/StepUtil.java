package test.support.util;

public class StepUtil {
  private static StepUtil instance;
  private StepUtil() {}

  public static final String BASE_PATH = UserUtil.BY_ID_PATH + "/steps";
  public static final String BY_ID_PATH = BASE_PATH + "/{stepId}";

  /**
   * TODO: This should be boilerplate to create a specific step.
   *
   * @return created step id
   */
  public long createStep() {
    // FIXME
    return 110719150L;
  }

  public static StepUtil getInstance() {
    if (instance == null) {
      instance = new StepUtil();
    }
    return instance;
  }
}
