package test.support.util;

public class Questions {
  private static final Questions INSTANCE = new Questions();
  private Questions() {}

  // TODO: This is intended to be boilerplate to create some question
  public long createQuestion() {
    return 0;
  }

  public static Questions getInstance() {
    return INSTANCE;
  }
}
