package test.wdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.support.util.GuestRequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Questions")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/questions";
  public static final String BY_NAME_PATH = BASE_PATH + "/{question}";

  private final GuestRequestFactory req;

  public QuestionsTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getQuestions() {
    getQuestionList();
  }

  @ParameterizedTest(name = "GET " + BASE_PATH + "/{arguments}")
  @DisplayName("GET " + BY_NAME_PATH)
  @MethodSource("getQuestionList")
  void getQuestionDetails(String name) {
    req.jsonSuccessRequest().when().get(BY_NAME_PATH, name);
  }

  public String[] getQuestionList() {
    return req.jsonSuccessRequest().when().get(BASE_PATH).as(String[].class);
  }
}
