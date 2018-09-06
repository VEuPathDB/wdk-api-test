package test.service.users.steps;

import io.restassured.http.ContentType;
import org.gusdb.wdk.model.api.AnalysisSummary;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.service.users.StepsTest;
import test.support.util.Auth;
import test.support.util.Requests;
import test.support.util.StepAnalysis;
import test.support.util.StepAnalysis.Paths;
import test.support.util.Steps;

import static org.apache.http.HttpStatus.*;

@DisplayName("Step Analysis")
class StepAnalysisTest extends StepsTest {
  private static final long INVALID_STEP_ID = -1L;
  private static final long INVALID_ANALYSIS_ID = -1L;

  private final Steps steps;

  private final StepAnalysis stepAnalysis;

  private long stepId;

  StepAnalysisTest(StepAnalysis stepAnalysis, Steps steps, Auth auth, Requests req) {
    super(auth, req);
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

  @Nested
  @DisplayName("GET " + Paths.BASE)
  class GetAnalyses {
    // TODO: Invalid user id
    // TODO: Invalid step id
    // TODO: Invalid analysis id

    @Test
    @DisplayName("should return a list of analyses for successful request")
    void getAnalysesList() {
      req.authJsonRequest(SC_OK)
          .when()
          .get(Paths.BASE, DEFAULT_USER, stepId);
    }
  }

  /**
   * Tests for GET requests to the base analyses endpoint
   */
  @Nested
  @DisplayName("POST " + Paths.BASE)
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  class PostAnalyses {

    @ParameterizedTest
    @DisplayName("should return 404 when the user id is invalid")
    @MethodSource("getNewAnalysisPostBodies")
    void userIsInvalid(final NewAnalysisRequest body) {
      req.authJsonPayloadRequest(body, SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .post(Paths.BASE, INVALID_USER, stepId);
    }

    @ParameterizedTest
    @DisplayName("should return 404 when the step id is invalid")
    @MethodSource("getNewAnalysisPostBodies")
    void stepIsInvalid(final NewAnalysisRequest body) {
      req.authJsonPayloadRequest(body, SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .post(Paths.BASE, DEFAULT_USER, INVALID_STEP_ID);
    }

    @ParameterizedTest
    @DisplayName("should create new analysis when request is correct")
    @MethodSource("getNewAnalysisPostBodies")
    void createAnalysisInstance(NewAnalysisRequest body) {
      StepAnalysisPluginResponse resp = req.authJsonPayloadRequest(body, SC_OK)
          .when()
          .post(Paths.BASE, DEFAULT_USER, stepId)
          .as(StepAnalysisPluginResponse.class);

      // Cleanup
      stepAnalysis.deleteStepAnalysis(DEFAULT_USER, stepId,
          resp.getAnalysisId());
    }

    private NewAnalysisRequest[] getNewAnalysisPostBodies() {
      return stepAnalysis.analysisCreateRequestFac();
    }
  }

  /**
   * Tests for GET requests to the step analysis result endpoint
   */
  @Nested
  @DisplayName("GET " + Paths.RESULT)
  class GetAnalysisResult {

    long analysisId;

    @BeforeEach
    void createAnalysis() {
      analysisId = stepAnalysis.newGoEnrichment(DEFAULT_USER, stepId)
          .getAnalysisId();
      stepAnalysis.populateGoEnrichment(DEFAULT_USER, stepId, analysisId);
    }

    @AfterEach
    void destroyAnalysis() {
      stepAnalysis.deleteStepAnalysis(DEFAULT_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 422 when the instance has not yet run")
    void instanceHasNotRun() {
      req.authRequest(SC_UNPROCESSABLE_ENTITY, ContentType.TEXT)
          .when()
          .get(Paths.RESULT, DEFAULT_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the user id is invalid")
    void userIsInvalid() {
      // Setup
      stepAnalysis.runAnalysis(DEFAULT_USER, stepId, analysisId);

      // Test
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.RESULT, INVALID_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the step id is invalid")
    void stepIsInvalid() {
      // Setup
      stepAnalysis.runAnalysis(DEFAULT_USER, stepId, analysisId);

      // Test
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.RESULT, DEFAULT_USER, INVALID_STEP_ID, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the analysis id is invalid")
    void analysisIsInvalid() {
      // Setup
      stepAnalysis.runAnalysis(DEFAULT_USER, stepId, analysisId);

      // Test
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.RESULT, DEFAULT_USER, stepId, INVALID_ANALYSIS_ID);
    }

    @Test
    @DisplayName("should return 200 when given a correct request")
    void correctRequest() {
      // Setup
      stepAnalysis.runAnalysis(DEFAULT_USER, stepId,
          analysisId);

      // Test
      req.authRequest(SC_OK, ContentType.JSON)
          .when()
          .get(Paths.RESULT, DEFAULT_USER, stepId, analysisId);
    }
  }

  /**
   * Tests for POST requests to the step analysis result endpoint
   */
  @Nested
  @DisplayName("POST " + Paths.RESULT)
  class PostAnalysisResult {

    private long analysisId;

    @BeforeEach
    void createAnalysis() {
      analysisId = stepAnalysis.newGoEnrichment(DEFAULT_USER, stepId)
          .getAnalysisId();
    }

    @AfterEach
    void destroyAnalysis() {
      stepAnalysis.deleteStepAnalysis(DEFAULT_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the user id is invalid")
    void userIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .post(Paths.RESULT, INVALID_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the step id is invalid")
    void stepIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .post(Paths.RESULT, DEFAULT_USER, INVALID_STEP_ID, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the analysis id is invalid")
    void analysisIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .post(Paths.RESULT, DEFAULT_USER, stepId, INVALID_ANALYSIS_ID);
    }

    @Test
    @DisplayName("should run the analysis when request is correct")
    void runAnalysisInstance() {
      req.authRequest(SC_OK, ContentType.JSON)
          .when()
          .post(Paths.RESULT, DEFAULT_USER, stepId, analysisId);
    }
  }

  /**
   * Tests for GET requests to the step analysis result status endpoint
   */
  @Nested
  @DisplayName("GET " + Paths.STATUS)
  class GetAnalysisResultStatus {

    private long analysisId;

    @BeforeEach
    void createAnalysis() {
      analysisId = stepAnalysis.newGoEnrichment(DEFAULT_USER, stepId)
          .getAnalysisId();
    }

    @AfterEach
    void destroyAnalysis() {
      stepAnalysis.deleteStepAnalysis(DEFAULT_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the user id is invalid")
    void userIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.STATUS, INVALID_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the step id is invalid")
    void stepIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.STATUS, DEFAULT_USER, INVALID_STEP_ID, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the analysis id is invalid")
    void analysisIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.STATUS, DEFAULT_USER, stepId, INVALID_ANALYSIS_ID);
    }

    @Test
    @DisplayName("should return 200 with status when request is correct")
    void getAnalysisResultStatus() {
      req.authRequest(SC_OK, ContentType.JSON)
          .when()
          .get(Paths.STATUS, DEFAULT_USER, stepId, analysisId);
    }
  }

  /**
   * Tests for GET requests to the step analysis by id endpoint
   */
  @Nested
  @DisplayName("GET " + Paths.BY_ID)
  class GetAnalysisDetails {

    private long analysisId;

    @BeforeEach
    void createAnalysis() {
      analysisId = stepAnalysis.newGoEnrichment(DEFAULT_USER, stepId)
          .getAnalysisId();
    }

    @AfterEach
    void destroyAnalysis() {
      stepAnalysis.deleteStepAnalysis(DEFAULT_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the user id is invalid")
    void userIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.BY_ID, INVALID_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the step id is invalid")
    void stepIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.BY_ID, DEFAULT_USER, INVALID_STEP_ID, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the analysis id is invalid")
    void analysisIsInvalid() {
      req.authRequest(SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.BY_ID, DEFAULT_USER, stepId, INVALID_ANALYSIS_ID);
    }

    @Test
    @DisplayName("should return analysis details when request is correct")
    void getAnalysisDetails() {
      req.authRequest(SC_OK, ContentType.JSON)
          .when()
          .get(Paths.BY_ID, DEFAULT_USER, stepId, analysisId);
    }
  }

  /**
   * Tests for PATCH requests to the step analysis by id endpoint
   */
  @Nested
  @DisplayName("PATCH " + Paths.BY_ID)
  class PatchAnalysis {

    private long analysisId;

    @BeforeEach
    void createAnalysis() {
      analysisId = stepAnalysis.newGoEnrichment(DEFAULT_USER, stepId).getAnalysisId();
    }

    @AfterEach
    void destroyAnalysis() {
      stepAnalysis.deleteStepAnalysis(DEFAULT_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the user id is invalid")
    void userIsInvalid() {
      final StepAnalysisPlugin<GoEnrichmentFormParams> in;

      in = stepAnalysis.newGoEnrichmentParamsBody();
      in.getFormParams().setpValueCutoff(new String[]{"0.07"});

      req.authJsonPayloadRequest(in, SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.BY_ID, INVALID_USER, stepId, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the step id is invalid")
    void stepIsInvalid() {
      final StepAnalysisPlugin<GoEnrichmentFormParams> in;

      in = stepAnalysis.newGoEnrichmentParamsBody();
      in.getFormParams().setpValueCutoff(new String[]{"0.07"});

      req.authJsonPayloadRequest(in, SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.BY_ID, DEFAULT_USER, INVALID_STEP_ID, analysisId);
    }

    @Test
    @DisplayName("should return 404 when the analysis id is invalid")
    void analysisIsInvalid() {
      final StepAnalysisPlugin<GoEnrichmentFormParams> in;

      in = stepAnalysis.newGoEnrichmentParamsBody();
      in.getFormParams().setpValueCutoff(new String[]{"0.07"});

      req.authJsonPayloadRequest(in, SC_NOT_FOUND, ContentType.TEXT)
          .when()
          .get(Paths.BY_ID, DEFAULT_USER, stepId, INVALID_ANALYSIS_ID);
    }

    // TODO: OK update display name

    @Test
    @DisplayName("PATCH " + Paths.BY_ID)
    void updateAnalysisInstance() {
      final StepAnalysisPlugin<GoEnrichmentFormParams> in;
      final GoEnrichmentResponse out;

      in = stepAnalysis.newGoEnrichmentParamsBody();
      in.getFormParams().setpValueCutoff(new String[]{"0.07"});

      req.authJsonPayloadRequest(in, SC_OK, ContentType.JSON)
          .when()
          .get(Paths.BY_ID, DEFAULT_USER, stepId, analysisId);

      out = req.authJsonRequest(SC_OK)
          .when()
          .get(Paths.BY_ID, DEFAULT_USER, stepId, analysisId)
          .as(GoEnrichmentResponse.class);

      Assertions.assertEquals(in, out);
    }
  }

  /**
   * Tests for DELETE requests to the step analysis by id endpoint.
   */
  @Nested
  @DisplayName("DELETE " + Paths.BY_ID)
  class DeleteAnalysisInstance {
    // TODO: Invalid user id
    // TODO: Invalid step id
    // TODO: Invalid analysis id

    @Test
    @Disabled
    @DisplayName("DELETE " + Paths.BY_ID)
    void deleteAnalysisInstance(final AnalysisSummary analysis) {
      req.authRequest(SC_NO_CONTENT, ContentType.TEXT)
          .when()
          .delete(Paths.BY_ID, DEFAULT_USER, stepId, analysis.getId());
    }
  }
}
