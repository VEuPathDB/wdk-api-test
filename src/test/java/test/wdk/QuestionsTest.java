package test.wdk;

import static test.support.util.SearchUtil.BASE_URI;
import static test.support.util.SearchUtil.KEYED_URI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import test.support.Category;
import test.support.util.GuestRequestFactory;

@DisplayName("Questions")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionsTest extends TestBase {
  public static final String BASE_PATH = BASE_URI;
  public static final String BY_NAME_PATH = KEYED_URI;

  private final GuestRequestFactory req;

  public QuestionsTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("GET all transcript questions, without parameters")
  void getQuestions() {
    req.jsonSuccessRequest().when().get(BASE_URI, "transcript");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test a single question (exon count)")
  void getExonCountQuestion() {
    req.jsonSuccessRequest().when().get(KEYED_URI, "gene", "GenesByExonCount");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test a single question (exon count)")
  void getSrtQuestion() {
    req.jsonSuccessRequest().when().get(KEYED_URI, "transcript", "SRT");
  }
}
