package test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnalysisTypeSummary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Analysis Types API")
public class AnalysisTypeTest extends StepsTest {

  public static final String ANALYSIS_TYPES_PATH = STEP_BY_ID_PATH + "/analysis-types";
  public static final String ANALYSIS_TYPE_BY_NAME_PATH = ANALYSIS_TYPES_PATH + "/{analysisTypeName}";

  protected static long stepId;

  @Test
  @DisplayName("List Available Types")
  void listTypes() {
    getAnalysisTypeList();
  }

  @ParameterizedTest
  @DisplayName("View Analysis Type Details")
  @MethodSource("getAnalysisTypeList")
  void viewAnalysisType(AnalysisTypeSummary summary) {
    prepAuthRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(ANALYSIS_TYPE_BY_NAME_PATH, DEFAULT_USER, stepId, summary.getName());
  }

  /**
   * Retrieve the parsed payload from the step analysis type list endpoint.
   *
   * @return a parsed array of step analysis instance type summaries.
   */
  public static AnalysisTypeSummary[] getAnalysisTypeList() {
    return prepAuthRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(ANALYSIS_TYPES_PATH, DEFAULT_USER, stepId)
      .getBody()
        .as(AnalysisTypeSummary[].class);
  }

  @BeforeAll
  static void pickStep() {
    // FIXME: THis should be automated
    stepId = 110719150L;
  }
}
