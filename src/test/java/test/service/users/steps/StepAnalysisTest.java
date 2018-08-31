package test.service.users.steps;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnalysisSummary;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.service.users.StepsTest;
import test.support.util.Auth;
import test.support.util.Steps;

@DisplayName("Step Analysis")
class StepAnalysisTest extends StepsTest {
  public static final String BASE_PATH = StepsTest.BY_ID_PATH + "/analyses";
  public static final String BY_ID_PATH = BASE_PATH + "/{analysisId}";
  public static final String RESULT_PATH = BY_ID_PATH + "/result";
  public static final String STATUS_PATH = RESULT_PATH + "/status";

  private final Steps steps;

  private long stepId;

  StepAnalysisTest(Steps steps, Auth auth) {
    super(auth);
    this.steps = steps;
  }

  @BeforeEach
  void createStep() {
    stepId = steps.createStep();
  }

  @AfterEach
  void deleteStep() {
    // TODO: steps.deleteStep() ???
  }

  @Test
  @DisplayName("Get Analysis List")
  void getAnalysesList() {
    getAnalysisList();
  }

  @ParameterizedTest
  @DisplayName("Get Analysis Result")
  @MethodSource("getAnalysisList")
  void getAnalysisResult(final AnalysisSummary analysis) {
    auth.prepRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(RESULT_PATH, DEFAULT_USER, stepId, analysis.getId());
  }

  @ParameterizedTest
  @DisplayName("Get Analysis Result Status")
  @MethodSource("getAnalysisList")
  void getAnalysisResultStatus(AnalysisSummary analysis) {
    auth.prepRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(STATUS_PATH, DEFAULT_USER, stepId, analysis.getId());
  }

  @ParameterizedTest
  @DisplayName("Get Analysis Details")
  @MethodSource("getAnalysisList")
  void getAnalysisDetails(AnalysisSummary analysis) {
    auth.prepRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(BY_ID_PATH, DEFAULT_USER, stepId, analysis.getId());
  }

  @Test
  @Disabled
  @DisplayName("Update Analysis Instance")
  void updateAnalysisInstance() {}

  @Test
  @Disabled
  @DisplayName("Create Analysis Instance")
  void createAnalysisInstance() {}

  /**
   * Retrieve the parsed payload from the step analysis instance list endpoint.
   *
   * @return a parsed array of step analysis instance summaries.
   */
  protected AnalysisSummary[] getAnalysisList() {
    return auth.prepRequest()
      .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(BASE_PATH, DEFAULT_USER, stepId)
        .getBody().as(AnalysisSummary[].class);
  }
}
