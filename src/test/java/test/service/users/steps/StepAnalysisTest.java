package test.service.users.steps;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnalysisSummary;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.GoEnrichmentFormParams;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.GoEnrichmentResponse;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.StepAnalysisPlugin;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.service.users.StepsTest;
import test.support.util.Auth;
import test.support.util.StepAnalysis;
import test.support.util.Steps;

@DisplayName("Step Analysis")
class StepAnalysisTest extends StepsTest {
  public static final String BASE_PATH = StepsTest.BY_ID_PATH + "/analyses";
  public static final String RESULT_PATH = StepAnalysis.BY_ID_PATH + "/result";
  public static final String STATUS_PATH = RESULT_PATH + "/status";

  private final Steps steps;

  private final StepAnalysis stepAnalysis;

  private long stepId;

  StepAnalysisTest(StepAnalysis stepAnalysis, Steps steps, Auth auth) {
    super(auth);
    this.steps = steps;
    this.stepAnalysis = stepAnalysis;
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
  @DisplayName("GET " + StepAnalysis.BASE_PATH)
  void getAnalysesList() {
    getAnalysisList();
  }

  @Test
  @Disabled
  @DisplayName("POST " + StepAnalysis.BASE_PATH)
  void createAnalysisInstance() {
    auth.prepRequest()
        .contentType(ContentType.JSON)
        .body(stepAnalysis.newCreateGoEnrichmentRequestBody())
        .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .when()
        .post(BASE_PATH, DEFAULT_USER, stepId);
  }

  @ParameterizedTest
  @DisplayName("GET " + RESULT_PATH)
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
  @DisplayName("POST " + RESULT_PATH)
  @MethodSource("getAnalysisList")
  void runAnalysisInstance(final AnalysisSummary analysis) {
    auth.prepRequest()
        .expect()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.SC_OK)
        .when()
        .post(RESULT_PATH, DEFAULT_USER, stepId, analysis.getId());
  }

  @ParameterizedTest
  @DisplayName("GET " + STATUS_PATH)
  @MethodSource("getAnalysisList")
  void getAnalysisResultStatus(final AnalysisSummary analysis) {
    auth.prepRequest()
        .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .when()
        .get(STATUS_PATH, DEFAULT_USER, stepId, analysis.getId());
  }

  @ParameterizedTest
  @DisplayName("GET " + StepAnalysis.BY_ID_PATH)
  @MethodSource("getAnalysisList")
  void getAnalysisDetails(final AnalysisSummary analysis) {
    auth.prepRequest()
        .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .when()
        .get(StepAnalysis.BY_ID_PATH, DEFAULT_USER, stepId, analysis.getId());
  }

  @ParameterizedTest(name = "Patch {arguments}")
  @DisplayName("PATCH " + StepAnalysis.BY_ID_PATH)
  @MethodSource("getAnalysisList")
  void updateAnalysisInstance(final AnalysisSummary analysis) {
    final StepAnalysisPlugin<GoEnrichmentFormParams> in;
    final GoEnrichmentResponse out;

    in = stepAnalysis.newGoEnrichmentParamsBody();

    in.getFormParams().setpValueCutoff(new String[]{"0.07"});

    auth.prepRequest()
        .contentType(ContentType.JSON)
        .body(stepAnalysis.newGoEnrichmentParamsBody())
        .expect()
        .statusCode(HttpStatus.SC_NO_CONTENT)
        .when()
        .get(StepAnalysis.BY_ID_PATH, DEFAULT_USER, stepId, analysis.getId());


    out = auth.prepRequest()
        .expect()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.SC_OK)
        .when()
        .get(StepAnalysis.BY_ID_PATH, DEFAULT_USER, stepId, analysis.getId())
        .as(GoEnrichmentResponse.class);

    Assertions.assertEquals(in, out);
  }

  @Test
  @Disabled
  @DisplayName("DELETE " + StepAnalysis.BY_ID_PATH)
  void deleteAnalysisInstance(final AnalysisSummary analysis) {
    auth.prepRequest()
        .expect()
        .contentType(ContentType.JSON)
        .statusCode(HttpStatus.SC_NO_CONTENT)
        .when()
        .delete(StepAnalysis.BY_ID_PATH, DEFAULT_USER, stepId, analysis.getId());
  }

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

  protected AnalysisSummary[] getNewAnalyses() {
    return new AnalysisSummary[]{
        stepAnalysis.newGoEnrichment(DEFAULT_USER, stepId)
    };
  }
}
