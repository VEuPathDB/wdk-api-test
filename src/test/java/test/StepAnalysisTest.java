package test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnalysisSummary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Step Analysis API")
public class StepAnalysisTest extends StepsTest {
  public static final String ANALYSES_PATH = STEP_BY_ID_PATH + "/analyses";
  public static final String ANALYSIS_BY_ID_PATH = ANALYSES_PATH + "/{analysisId}";

  protected static long stepId;

  @Test
  @DisplayName("List Analyses")
  void testAnalysesList() {
    getAnalysisList();
  }

  @ParameterizedTest
  @DisplayName("Get Analysis Details")
  @MethodSource("getAnalysisList")
  void getAnalysisDetails(AnalysisSummary analysis) {
    prepAuthRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(ANALYSIS_BY_ID_PATH, DEFAULT_USER, stepId, analysis.getAnalysisId());
  }

  /**
   * Retrieve the parsed payload from the step analysis instance list endpoint.
   *
   * @return a parsed array of step analysis instance summaries.
   */
  protected static AnalysisSummary[] getAnalysisList() {
    return prepAuthRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(ANALYSES_PATH, DEFAULT_USER, stepId)
        .getBody().as(AnalysisSummary[].class);
  }

  @BeforeAll
  static void pickStep() {
    // FIXME: Retrieve this from step API
    stepId = 110719150L;
  }
}
