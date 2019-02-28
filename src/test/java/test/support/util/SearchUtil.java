package test.support.util;

public class SearchUtil {
  private static SearchUtil instance;
  private SearchUtil() {}

  // TODO: This is intended to be boilerplate to create some question
  public long createSearch() {
    return 0;
  }

  public static SearchUtil getInstance() {
    if (instance == null) {
      instance = new SearchUtil();
    }

    return instance;
  }
}
