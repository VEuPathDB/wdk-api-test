package test.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Questions")
public class QuestionsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/questions";
  public static final String BY_NAME_PATH = BASE_PATH + "/{question}";

  @Test
  @DisplayName("Get Question List")
  void getQuestions() {
    getQuestionList();
  }

  @ParameterizedTest(name = "Get details for {arguments}")
  @DisplayName("Get Question Details")
  @MethodSource("getQuestionList")
  void getQuestionDetails(String name) {
    RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(BY_NAME_PATH, name);
  }

  public static String[] getQuestionList() {
    return RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(BASE_PATH)
        .as(String[].class);
  }
}
