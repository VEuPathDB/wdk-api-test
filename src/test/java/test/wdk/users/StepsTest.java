package test.wdk.users;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.SortSpec;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.StepRequestBody;
import org.gusdb.wdk.model.api.StepDisplayPreferences;
import org.gusdb.wdk.model.api.StepMeta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.ReportUtil;
import test.support.util.AuthUtil;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.support.util.StepUtil;
import test.support.util.UserUtil;
import test.wdk.UsersTest;

/* TESTS TO RUN
 *
 - POST step/
  - with no step or dataset params
    - all params valid
    - not all valid - expect 422
  - with step param
   - if non-null - expect 422
  - with dataset param
   - if null should fail
   - not sure if we need extra test for this but can't hurt
  - with a filter
  - with and w/o valid/invalid legacy filter
  - with and w/o weight

 - GET step/{id}
   - valid ID success
   - invalid ID 404
   - confirm not run, not part of strat

 - DELETE step/{id}
  - invalid id = 404
  - with valid id, step in strategy = should fail
  - with valid id, step not in strategy = succeed

 - PATCH step/{id}
  - try modifying allowed fields -- should succeed
  - try modifying unallowed fields -- should fail

 - POST step/{id}/reports/standard
   ?? - should fail if modifying search name or stepParams
   - should update estimatedSize, lastRunTime

 - POST step/{id}/reports/???
   ?? - should fail if modifying search name or stepParams
   - should update estimatedSize, lastRunTime

 - PUT step/{id}/search-config
   - should fail if change a step param
   - should clear estimatedSize
   - should clear estmiatedSize of downstream steps if in strat
 */

@DisplayName("Steps")
public class StepsTest extends UsersTest {
  public static final String BASE_PATH = UsersTest.BY_ID_PATH + "/steps";
  public static final String BY_ID_PATH = BASE_PATH + "/{stepId}";
  public static final String REPORTS_PATH = BASE_PATH + "/{stepId}/reports/{reportName}";
  public static final Long INVALID_STEP_ID = -1L;

  protected final AuthUtil _authUtil;
  private GuestRequestFactory _guestRequestFactory;

  public StepsTest(
    AuthUtil auth,
    AuthenticatedRequestFactory authReqFactory,
    GuestRequestFactory guestReqFactory
  ) {
    super(authReqFactory);
    this._authUtil = auth;
    _guestRequestFactory = guestReqFactory;
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create, get and delete a valid guest step")
  void createAndGetAndDeleteGuestStep() throws JsonProcessingException {

    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    Response stepResponse =
        StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, ReportUtil.createValidExonCountSearchConfig(), "GenesByExonCount");

    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys

    // get the step
    StepUtil.getInstance().getStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_OK);

    // delete the step
    StepUtil.getInstance().deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

    // deleting again should get a not found
    StepUtil.getInstance().deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Get invalid step id")
  void getInvalidGuestStepId() throws JsonProcessingException {
    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    StepUtil.getInstance().getStep(INVALID_STEP_ID, _guestRequestFactory,  cookieId, HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create invalid guest step")
  void createInvalidGuestStep() throws JsonProcessingException {

    StepRequestBody step = new StepRequestBody(ReportUtil.createInvalidExonCountSearchConfig(), "GenesByExonCount");

    _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_UNPROCESSABLE_ENTITY)
      .when()
      .post(BASE_PATH, "current");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Delete invalid step ID")
  void deleteInvalidStepId() throws JsonProcessingException {

    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    StepUtil.getInstance().deleteStep(INVALID_STEP_ID, _guestRequestFactory, cookieId, HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create invalid transform step.  Not in strategy, so not allowed to have a non-null step param")
  void createInvalidTransformStep() throws JsonProcessingException {

    StepRequestBody leafStep = new StepRequestBody(ReportUtil.createValidExonCountSearchConfig(), "GenesByExonCount");

    Response stepResponse = _guestRequestFactory.jsonPayloadRequest(leafStep, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BASE_PATH, "current");

    long leafStepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");

    StepRequestBody transformStep = new StepRequestBody(StepUtil.getInstance().createInvalidOrthologsSearchConfig(_guestRequestFactory, leafStepId), "GenesByOrthologs");

    stepResponse = _guestRequestFactory.jsonPayloadRequest(transformStep, HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .request()
        .cookie("JSESSIONID", cookieId)
        .when()
        .post(BASE_PATH, "current");

    // delete the leaf step, to clean up
    StepUtil.getInstance().deleteStep(leafStepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with a step filter")
  void createStepWithValidStepFilter() throws JsonProcessingException {

    SearchConfig searchConfig = StepUtil.getInstance().createSearchConfigWithStepFilter("matched_transcript_filter_array");
    StepRequestBody step = new StepRequestBody(searchConfig, "GenesByExonCount");

    Response stepResponse =  _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_OK)
      .when()
      .post(BASE_PATH, "current");

    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");

    // delete the step
    StepUtil.getInstance().deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with invalid step filter")
  void createStepWithInvalidStepFilter() throws JsonProcessingException {

    SearchConfig searchConfig = StepUtil.getInstance().createSearchConfigWithStepFilter("sillyFilter");
    StepRequestBody step = new StepRequestBody(searchConfig, "GenesByExonCount");

    _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_UNPROCESSABLE_ENTITY)
    .when()
    .post(BASE_PATH, "current");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with a legacy filter")
  void createStepWithValidLegacyFilter() throws JsonProcessingException {

    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig();
    searchConfig.setLegacyFilterName("all_results");
    StepRequestBody step = new StepRequestBody(searchConfig, "GenesByExonCount");

    Response stepResponse =  _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_OK)
      .when()
      .post(BASE_PATH, "current");

    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");

    // delete the step
    StepUtil.getInstance().deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with invalid legacy filter")
  void createStepWithInvalidLegacyFilter() throws JsonProcessingException {

    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig();
    searchConfig.setLegacyFilterName("silly_filter");

    StepRequestBody step = new StepRequestBody(searchConfig, "GenesByExonCount");

    _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_UNPROCESSABLE_ENTITY)
    .when()
    .post(BASE_PATH, "current");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create, and patch a valid guest step")
  void validPatchStep() throws JsonProcessingException {

    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    Response stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, ReportUtil.createValidExonCountSearchConfig(), "GenesByExonCount");


    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys

    // the step prefs are not validated by the server.
    StepMeta stepPatch = new StepMeta();
    stepPatch.setCustomName("yippee");
    StepDisplayPreferences displayPrefs = new StepDisplayPreferences();
    stepPatch.setDisplayPreferences(displayPrefs);
    String[] colSel = {"happy", "birthday"};
    Map<String, SortSpec.SortDirection> sort = new HashMap<String, SortSpec.SortDirection>();
    sort.put("dragon", SortSpec.SortDirection.ASC);
    stepPatch.getDisplayPreferences().setColumnSelection(colSel);
    stepPatch.getDisplayPreferences().setColumnSorting(sort);

    _guestRequestFactory.jsonPayloadRequest(stepPatch, HttpStatus.SC_NO_CONTENT)
    .request()
    .cookie("JSESSIONID", cookieId)
    .when()
    .patch(BY_ID_PATH, "current", stepId);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("POST to standard step report.  Fail because not in strat")
  void validStepStandardReportNotInStrat() throws JsonProcessingException {

    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    Response stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, ReportUtil.createValidExonCountSearchConfig(), "GenesByExonCount");


    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys

    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();

    // fail because not in strategy
    _guestRequestFactory.jsonPayloadRequest(reportConfig, HttpStatus.SC_UNPROCESSABLE_ENTITY)
    .request()
    .cookie("JSESSIONID", cookieId)
    .when()
      .post(REPORTS_PATH, "current", stepId, "standard");

    // delete the step to clean up
    StepUtil.getInstance().deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("PUT a valid search config.  ")
  void putValidStepSearchConfig() throws JsonProcessingException {

    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    Response stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, ReportUtil.createValidExonCountSearchConfig(), "GenesByExonCount");

    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys

    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig();

    _guestRequestFactory.jsonPayloadRequest(searchConfig, HttpStatus.SC_NO_CONTENT)
    .request()
    .cookie("JSESSIONID", cookieId)
    .when()
    .put(BY_ID_PATH + "/search-config", "current", stepId);

    // delete the step to clean up
    StepUtil.getInstance().deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

  }

}

