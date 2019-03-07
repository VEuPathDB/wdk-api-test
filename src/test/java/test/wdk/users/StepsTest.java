package test.wdk.users;

import static test.support.Conf.SERVICE_PATH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.FilterValueSpec;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.SortSpec;
import org.gusdb.wdk.model.api.Step;
import org.gusdb.wdk.model.api.StepDisplayPreferences;
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
import test.support.util.RequestFactory;
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
  static final  Long INVALID_STEP_ID = new Long(-1);

  protected final AuthUtil _authUtil;
  private GuestRequestFactory _guestRequestFactory;

  public StepsTest(AuthUtil auth, AuthenticatedRequestFactory authReqFactory, GuestRequestFactory guestReqFactory ) {
    super(authReqFactory);
    this._authUtil = auth;
    _guestRequestFactory = guestReqFactory;
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create, get and delete a valid guest step")
  void createAndGetAndDeleteGuestStep() throws JsonProcessingException {
    
    Response stepResponse = createValidExonCountStepResponse(_guestRequestFactory);
    
    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");
    
    // get the step
    getStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_OK);

    // delete the step
    deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

    // deleting again should get a not found
    deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NOT_FOUND);
  }
   
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Get invalid step id")
  void getInvalidGuestStepId() throws JsonProcessingException {
    getStep(INVALID_STEP_ID, _guestRequestFactory, getIrrelevantCookieId(), HttpStatus.SC_NOT_FOUND);
  }
 
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create invalid guest step")
  void createInvalidGuestStep() throws JsonProcessingException {
    
    Step step = new Step(ReportUtil.createInvalidExonCountSearchConfig(_guestRequestFactory), "GenesByExonCount");
    
    _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_UNPROCESSABLE_ENTITY)
      .when()
      .post(BASE_PATH, "current");    
  }
 
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Delete invalid step ID")
  void deleteInvalidStepId() throws JsonProcessingException {
    
    deleteStep(INVALID_STEP_ID, _guestRequestFactory, getIrrelevantCookieId(), HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create invalid transform step")
  void createInvalidTransformStep() throws JsonProcessingException {
    
    Step leafStep = new Step(ReportUtil.createValidExonCountSearchConfig(_guestRequestFactory), "GenesByExonCount");
    
    Response stepResponse = _guestRequestFactory.jsonPayloadRequest(leafStep, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BASE_PATH, "current");    

    long leafStepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");
    
    Step transformStep = new Step(ReportUtil.createValidOrthologsSearchConfig(_guestRequestFactory, leafStepId), "GenesByOrthologs");

    stepResponse = _guestRequestFactory.jsonPayloadRequest(transformStep, HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .request()
        .cookie("JSESSIONID", cookieId)
        .when()
        .post(BASE_PATH, "current");    

    // delete the leaf step, to clean up
    deleteStep(leafStepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with a step filter")
  void createStepWithValidStepFilter() throws JsonProcessingException {
   
    SearchConfig searchConfig = createSearchConfigWithStepFilter("matchedTranscriptFilter");
    Step step = new Step(searchConfig, "GenesByExonCount");
    
    Response stepResponse =  _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_OK)
      .when()
      .post(BASE_PATH, "current");    
 
    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");

    // delete the step
    deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with invalid step filter")
  void createStepWithInvalidStepFilter() throws JsonProcessingException {
   
    SearchConfig searchConfig = createSearchConfigWithStepFilter("sillyFilter");
    Step step = new Step(searchConfig, "GenesByExonCount");  

    _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_UNPROCESSABLE_ENTITY)
    .when()
    .post(BASE_PATH, "current");    
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with a step filter")
  void createStepWithValidLegacyFilter() throws JsonProcessingException {
   
    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig(_guestRequestFactory);
    searchConfig.setLegacyFilterName("all_results");
    Step step = new Step(searchConfig, "GenesByExonCount");
    
    Response stepResponse =  _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_OK)
      .when()
      .post(BASE_PATH, "current");    
 
    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");

    // delete the step
    deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create a step with invalid step filter")
  void createStepWithInvalidLegacyFilter() throws JsonProcessingException {
    
    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig(_guestRequestFactory);
    searchConfig.setLegacyFilterName("silly_filter");

    Step step = new Step(searchConfig, "GenesByExonCount");  

    _guestRequestFactory.jsonPayloadRequest(step, HttpStatus.SC_UNPROCESSABLE_ENTITY)
    .when()
    .post(BASE_PATH, "current");    
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create, and patch a valid guest step")
  void validPatchStep() throws JsonProcessingException {
    
    Response stepResponse = createValidExonCountStepResponse(_guestRequestFactory);
    
    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");
    
    // the step prefs are not validated by the server.
    StepPatch stepPatch = new StepPatch();
    stepPatch.customName = "yippee";
    stepPatch.displayPrefs = new StepDisplayPreferences();
    String[] colSel = {"happy", "birthday"};
    Map<String, SortSpec.SortDirection> sort = new HashMap<String, SortSpec.SortDirection>();
    sort.put("dragon", SortSpec.SortDirection.ASC);
    stepPatch.displayPrefs.setColumnSelection(colSel);
    stepPatch.displayPrefs.setColumnSorting(sort);
    
    _guestRequestFactory.jsonPayloadRequest(stepPatch, HttpStatus.SC_OK, ContentType.JSON)
    .cookie("JSESSIONID", cookieId)
    .when()
    .patch(BY_ID_PATH, "current", stepId);    
  }

  
  /////////////////////////////////////////////////////////////////////////////////////
  //////////////////////// Helper methods /////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////

  
  public static Response createValidExonCountStepResponse(RequestFactory requestFactory) throws JsonProcessingException {

    Step step = new Step(ReportUtil.createValidExonCountSearchConfig(requestFactory), "GenesByExonCount");
    
    return requestFactory.jsonPayloadRequest(step, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BASE_PATH, "current");    
  }
 
   private SearchConfig createSearchConfigWithStepFilter(String filterName) throws JsonProcessingException {
    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig(_guestRequestFactory);
    
    // add filter to searchConfig
    FilterValueSpec filterSpec = new FilterValueSpec();
    filterSpec.setName(filterName);
    Map<String, Object> value = new HashMap<String, Object>();
    String[] matches = {"Y", "N"};
    value.put("values", matches);
    filterSpec.setValue(value);
    List<FilterValueSpec> filterSpecs = new ArrayList<FilterValueSpec>();
    filterSpecs.add(filterSpec);
    searchConfig.setFilters(filterSpecs);
    return searchConfig;
  }
  
   private void getStep(long stepId, RequestFactory requestFactory, String cookieId, int expectedStatus) throws JsonProcessingException {
     requestFactory.emptyRequest()
     .cookie("JSESSIONID", cookieId)
     .expect()
     .statusCode(expectedStatus)
     .when()
       .get(BY_ID_PATH, "current", stepId); 
   }

   private void deleteStep(long stepId, RequestFactory requestFactory, String cookieId, int expectedStatus) throws JsonProcessingException {

    requestFactory.emptyRequest()
    .cookie("JSESSIONID", cookieId)
    .expect()
    .statusCode(expectedStatus)
    .when()
      .delete(BY_ID_PATH, "current", stepId); 
  }
    
  private String getIrrelevantCookieId() {
    return _guestRequestFactory.successRequest()
        .when()
        .get(SERVICE_PATH)
        .getCookie("JSESSIONID");
  }
  
  private class StepPatch {
    @SuppressWarnings("unused")
    String customName;
    StepDisplayPreferences displayPrefs;
  }

}

