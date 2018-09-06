package test.service.users.steps;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnalysisTypeSummary;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.service.users.StepsTest;
import test.support.util.Auth;
import test.support.util.Requests;
import test.support.util.Steps;

@DisplayName("Analysis Type")
public class AnalysisTypeTest extends StepsTest {

  public static final String BASE_PATH = StepsTest.BY_ID_PATH + "/analysis-types";
  public static final String BY_NAME_PATH = BASE_PATH + "/{analysisTypeName}";

  private final Steps steps;

  private long stepId;

  AnalysisTypeTest(Steps steps, Auth auth, Requests req) {
    super(auth, req);
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
  @DisplayName("GET " + BASE_PATH)
  void listTypes() {
    getAnalysisTypeList();
  }

  @ParameterizedTest(name = "View details for {arguments}")
  @DisplayName("GET " + BY_NAME_PATH)
  @MethodSource("getAnalysisTypeList")
  void viewAnalysisType(AnalysisTypeSummary summary) {
    req.authJsonRequest(HttpStatus.SC_OK)
        .when()
        .get(BY_NAME_PATH, DEFAULT_USER, stepId, summary.getName());
  }

  /**
   * Retrieve the parsed payload from the step analysis type list endpoint.
   *
   * @return a parsed array of step analysis instance type summaries.
   */
  public AnalysisTypeSummary[] getAnalysisTypeList() {
    return req.authJsonRequest(HttpStatus.SC_OK)
        .when()
        .get(BASE_PATH, DEFAULT_USER, stepId)
        .as(AnalysisTypeSummary[].class);
  }
}
