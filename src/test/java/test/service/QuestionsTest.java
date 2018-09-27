package test.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.support.util.RequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Questions")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/questions";
  public static final String BY_NAME_PATH = BASE_PATH + "/{question}";

  private final RequestFactory req;

  public QuestionsTest(RequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("Get Question List")
  void getQuestions() {
    getQuestionList();
  }

  @ParameterizedTest(name = "Get details for {arguments}")
  @DisplayName("Get Question Details")
  @MethodSource("getQuestionList")
  void getQuestionDetails(String name) {
    req.jsonSuccessRequest().when().get(BY_NAME_PATH, name);
  }

  public String[] getQuestionList() {
    return req.jsonSuccessRequest().when().get(BASE_PATH).as(String[].class);
  }
}
