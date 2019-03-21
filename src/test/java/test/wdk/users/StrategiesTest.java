package test.wdk.users;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.StepTreeNode;
import org.gusdb.wdk.model.api.StrategyCopyRequest;
import org.gusdb.wdk.model.api.StrategyCreationRequest;
import org.gusdb.wdk.model.api.StrategyListItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.ReportUtil;
import test.support.util.StrategyUtil;
import test.support.util.StepUtil;
import test.support.util.AuthUtil;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.wdk.StrategyListTest;
import test.wdk.UsersTest;

/*
tests to run
 - GET strategies
 
 - POST strategies
   - using step-tree
     - success case
     - fail if a step already belongs to a strategy
     - fail if bum step tree
     - saved
     - public
   - using sourceStrategySignature property
   
 - PATCH
   - set isDeleted.  following GET should fail.  unset it.  GET should pass
   
 - GET strategies/{id}
   - success
   - invalid id
   - try multi step strat
   
  - PUT strategies/{id}
    - with valid tree
    - with invalid (step part of another strategy)
  
  - POST strategies/{id}/duplicated-step-tree
    - valid case
   
 - POST step/{id}/reports/standard (for a step that is in a strategy)
   ?? - should fail if modifying search name or stepParams
   - should update estimatedSize, lastRunTime

 - POST step/{id}/reports/??? (for a step that is in a strategy)
   ?? - should fail if modifying search name or stepParams
   - should update estimatedSize, lastRunTime

 - PUT step/{id}/search-config (for a step that is in a strategy)
   - should clear estmiatedSize of downstream steps if in strat
   - not allowed to change answerParam
   
   - DELETE step/{id} for a step in a strat - should fail

 *
 */
  @DisplayName("Strategies")
  public class StrategiesTest extends UsersTest {
    public static final String BASE_PATH = UsersTest.BY_ID_PATH + "/strategies";
    public static final String BY_ID_PATH = BASE_PATH + "/{strategyId}";

    protected final AuthUtil _authUtil;
    private GuestRequestFactory _guestRequestFactory;

    public StrategiesTest(AuthUtil auth, AuthenticatedRequestFactory authReqFactory, GuestRequestFactory guestReqFactory ) {
      super(authReqFactory);
      this._authUtil = auth;
      _guestRequestFactory = guestReqFactory;
    }
    
    @Test
    @DisplayName("create single step strategy") 
    @Tag (Category.PLASMO_TEST)
    void createValidSingleStepStrat() throws JsonProcessingException {
      Response stepResponse = StepUtil.getInstance().createValidExonCountStepResponse(_guestRequestFactory);
      
      long stepId = stepResponse
          .body()
          .jsonPath()
          .getLong("id"); // TODO: use JsonKeys
      String cookieId = stepResponse.getCookie("JSESSIONID");
      
      StepTreeNode stepTree = new StepTreeNode();
      stepTree.setStepId(stepId);
      StrategyCreationRequest strat = new StrategyCreationRequest("my strat", stepTree);   
      
      Response stratResponse = _guestRequestFactory.jsonPayloadRequest(strat, HttpStatus.SC_OK, ContentType.JSON)
        .request().cookie("JSESSIONID", cookieId).when().post(BASE_PATH,
          "current");

      long strategyId = stratResponse
          .body()
          .jsonPath()
          .getLong("id"); // TODO: use JsonKeys

      // get the step
      StrategyUtil.getInstance().getStrategy(strategyId, _guestRequestFactory, cookieId, HttpStatus.SC_OK);

      // delete the Strategy
      StrategyUtil.getInstance().deleteStrategy(strategyId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

      // deleting again should get a not found
      StrategyUtil.getInstance().deleteStrategy(strategyId, _guestRequestFactory, cookieId, HttpStatus.SC_NOT_FOUND);

      
    }
    
    @Test
    @DisplayName("Run public strategy")
    @Disabled("Strategy service is not ready for this test")
    @Tag (Category.PLASMO_TEST)
    void runPublicStrategy() throws JsonProcessingException {
      // find a random strategy:  first one in list of public strats
      StrategyListItem[] strategyList =  _guestRequestFactory.jsonSuccessRequest().when().get(StrategyListTest.PUBLIC_STRATS_PATH).as(StrategyListItem[].class);
      String signature = strategyList[0].getSignature();
      signature = "3975e694d0bf0f69"; // TODO: fix this
      StrategyCopyRequest strategy = new StrategyCopyRequest();
      strategy.setSourceSignature(signature);
      
      // use its signature in request for strategy, which creates a copy of it
      Response response = _guestRequestFactory.jsonPayloadRequest(strategy, HttpStatus.SC_OK, ContentType.JSON)
          .when()
          .post(BASE_PATH, "current");    
      
      long rootStepId = response.body().jsonPath().getLong("latestStepId");
 
      StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();

      _guestRequestFactory.jsonPayloadRequest(reportConfig, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(StepsTest.BY_ID_PATH + "/reports/standard", "current", rootStepId);    
    }

}
