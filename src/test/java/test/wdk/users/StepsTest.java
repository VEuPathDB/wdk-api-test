package test.wdk.users;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.Step;
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
   - if non-null should fail
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

  protected final AuthUtil _authUtil;
  private GuestRequestFactory _guestRequestFactory;

  public StepsTest(AuthUtil auth, AuthenticatedRequestFactory authReqFactory, GuestRequestFactory guestReqFactory ) {
    super(authReqFactory);
    this._authUtil = auth;
    _guestRequestFactory = guestReqFactory;
  }
  
  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create and delete a guest step")
  void createAndDeleteGuestStep() throws JsonProcessingException {
    
    Response stepResponse = createExonCountStepResponse(_guestRequestFactory);
    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys
    String cookieId = stepResponse.getCookie("JSESSIONID");

    // delete the step
    deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

    // deleting again should get a not found
    deleteStep(stepId, _guestRequestFactory, cookieId, HttpStatus.SC_NOT_FOUND);
  }
  
  public static Response createExonCountStepResponse(RequestFactory requestFactory) throws JsonProcessingException {

    Step step = new Step(ReportUtil.createExonCountSearchConfig(requestFactory), "GenesByExonCount");
    
    return requestFactory.jsonPayloadRequest(step, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BASE_PATH, "current");    
  }
  
  private void deleteStep(long stepId, RequestFactory requestFactory, String cookieId, int expectedStatus) throws JsonProcessingException {

    requestFactory.emptyRequest()
    .cookie("JSESSIONID", cookieId)
    .expect()
    .statusCode(expectedStatus)
    .when()
      .delete(BY_ID_PATH, "current", stepId); 
  }
}

