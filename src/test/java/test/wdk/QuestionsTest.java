package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/*
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
*/

import test.support.Category;
import test.support.util.GuestRequestFactory;

@DisplayName("Questions")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/record-types/{record-type}/searches";
  public static final String BY_NAME_PATH = BASE_PATH + "/{searchName}";

  private final GuestRequestFactory req;

  public QuestionsTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("GET all transcript questions, without parameters")
  void getQuestions() {
    req.jsonSuccessRequest().when().get(BASE_PATH, "transcript");
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test a single question (exon count)")
  void getExonCountQuestion() {
    req.jsonSuccessRequest().when().get(BY_NAME_PATH, "gene", "GenesByExonCount");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test a single question (exon count)")
  void getSrtQuestion() {
    req.jsonSuccessRequest().when().get(BY_NAME_PATH, "transcript", "SRT");
  }


  /* commented out for now: this needs to iterate across all record classes, and then all searches
 
  // get all questions in detail
  @ParameterizedTest(name = "GET " + BASE_PATH + "/{arguments}")
  @Tag (Category.PRERELEASE_TEST) // this is an expensive test (runs all vocab queries).  only do at prerelease
  @DisplayName("GET " + BY_NAME_PATH)
  @MethodSource("getQuestionList")
  void getQuestionDetails(String name) {
    req.jsonSuccessRequest().when().get(BY_NAME_PATH, name);
  }

  public String[] getQuestionList(String recordClassUrlSegment) {
    return req.jsonSuccessRequest().when().get(BASE_PATH, recordClassUrlSegment).as(String[].class);
  }
   
  */
 
}
