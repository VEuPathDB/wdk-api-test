package test.support.util;

public class QuestionUtil {
  private static QuestionUtil instance;
  private QuestionUtil() {}

  // TODO: This is intended to be boilerplate to create some question
  public long createQuestion() {
    return 0;
  }

  public static QuestionUtil getInstance() {
    if (instance == null) {
      instance = new QuestionUtil();
    }

    return instance;
  }
}
