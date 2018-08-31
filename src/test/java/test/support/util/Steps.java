package test.support.util;

public class Steps {

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
