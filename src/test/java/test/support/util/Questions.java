package test.support.util;

public class Questions {
  private static Questions instance;
  private Questions() {}

  // TODO: This is intended to be boilerplate to create some question
  public long createQuestion() {
    return 0;
  }

  public static Questions getInstance() {
    if (instance == null) {
      instance = new Questions();
    }

    return instance;
  }
}
