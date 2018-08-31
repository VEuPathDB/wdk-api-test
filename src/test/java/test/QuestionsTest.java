package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;

@DisplayName("Questions")
public class QuestionsTest extends TestBase {
  public static final String QUESTIONS_PATH = SERVICE_PATH + "/questions";
  public static final String QUESTION_BY_NAME_PATH = QUESTIONS_PATH + "/{question}";

  @Test
  @DisplayName("Get Question List")
  void getQuestions() {
    getQuestionList();
  }

  @ParameterizedTest
  @DisplayName("Get Question Details")
  @MethodSource("getQuestionList")
  void getQuestionDetails(String name) {
    RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(QUESTION_BY_NAME_PATH, name);
  }


  public static String[] getQuestionList() {
    return Arrays.copyOf(
      RestAssured.expect()
          .statusCode(HttpStatus.SC_OK)
          .contentType(ContentType.JSON)
          .when()
          .get(QUESTIONS_PATH).body()
          .as(String[].class),
      10
    );
  }
}
