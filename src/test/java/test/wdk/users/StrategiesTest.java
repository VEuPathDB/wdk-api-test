package test.wdk.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.StepTreeNode;
import org.gusdb.wdk.model.api.StrategyCopyRequest;
import org.gusdb.wdk.model.api.StrategyListItem;
import org.gusdb.wdk.model.api.StrategyResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.ReportUtil;
import test.support.util.StepUtil;
import test.support.util.StrategyUtil;
import test.support.util.UserUtil;
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
    public static final String DUPLICATE_TREE_PATH = BASE_PATH + "/{strategyId}/duplicated-step-tree";

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
      
      String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
      
      // create a step, and GET its ID
      Response stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, ReportUtil.createValidExonCountSearchConfig(), "GenesByExonCount");
      long stepId = stepResponse.body().jsonPath().getLong("id");  

      Long strategyId = StrategyUtil.getInstance().createAndPostValidSingleStepStrategy(_guestRequestFactory, cookieId, stepId);
      
      // GET the strategy
      Response strategyResponse = StrategyUtil.getInstance().getStrategy(strategyId, _guestRequestFactory,
          cookieId, HttpStatus.SC_OK);
      StrategyResponseBody strategy = strategyResponse.as(StrategyResponseBody.class);
      
      // confirm that the stepTree has our step ID
      long stepIdFromTree = strategy.getStepTree().getStepId();
      assertEquals(stepId, stepIdFromTree, "Expected stepTree.stepId (" + stepIdFromTree + ") to equal submitted stepId: " + stepId);

      // DELETE the Strategy
      StrategyUtil.getInstance().deleteStrategy(strategyId, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

      // DELETE again should get a not found
      StrategyUtil.getInstance().deleteStrategy(strategyId, _guestRequestFactory, cookieId, HttpStatus.SC_NOT_FOUND);
      
    }
    
  @Test
  @DisplayName("revise a strategy with a PUT.")
  @Tag(Category.PLASMO_TEST)
  void reviseStrategy() throws JsonProcessingException {

    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    
    // create a step, GET its ID, and use it to create single step strategy
    SearchConfig exonCountSrchConfig = ReportUtil.createValidExonCountSearchConfig();
    Response stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, exonCountSrchConfig, "GenesByExonCount");
    long firstLeafStepId = stepResponse.body().jsonPath().getLong("id"); 
    Long strategyId = StrategyUtil.getInstance().createAndPostValidSingleStepStrategy(_guestRequestFactory,
        cookieId, firstLeafStepId);

    // request a new leaf step to add to the tree (re-use the same search config, for simplicity)
    stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, exonCountSrchConfig, "GenesByExonCount");
    long secondLeafStepId = stepResponse.body().jsonPath().getLong("id");

    // request a combiner step to add to the tree
    SearchConfig combinerSrchConfig = StepUtil.getInstance().createValidBooleanSearchConfig(
                                                                                            _guestRequestFactory, "TranscriptRecordClasses.TranscriptRecordClass");
    String transcriptBooleanUrlSegment = "boolean_question_TranscriptRecordClasses_TranscriptRecordClass";
    stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, combinerSrchConfig, transcriptBooleanUrlSegment);
    long combineStepId = stepResponse.body().jsonPath().getLong("id");

    // make new tree
    StepTreeNode combineTree = new StepTreeNode(combineStepId);
    combineTree.setPrimaryInput(new StepTreeNode(firstLeafStepId));
    combineTree.setSecondaryInput(new StepTreeNode(secondLeafStepId));
    
    // do the PUT
    StrategyUtil.getInstance().putStrategy(_guestRequestFactory, cookieId, strategyId, combineTree,
        HttpStatus.SC_NO_CONTENT);
    
    // GET the revised strategy
    Response strategyResponse = StrategyUtil.getInstance().getStrategy(strategyId, _guestRequestFactory,
        cookieId, HttpStatus.SC_OK);
    StrategyResponseBody revisedStrategy = strategyResponse.as(StrategyResponseBody.class);
    
    // confirm that the response stepTree is the same as what we PUT
    assertEquals(combineTree.toString(), revisedStrategy.getStepTree().toString(),
        "Expected step trees to be equal. Submitted:  " +  combineTree + " received: " + revisedStrategy.getStepTree());
  
    // since we have a tree in hand, also test nesting
    StepTreeNode dupTree = _guestRequestFactory.jsonPayloadRequest(null, HttpStatus.SC_OK,
        ContentType.JSON).request().cookie("JSESSIONID", cookieId).when().post(DUPLICATE_TREE_PATH, "current", strategyId).as(StepTreeNode.class);
    combineTree.setSecondaryInput(dupTree);
    
    // PUT the strat with nested tree
    StrategyUtil.getInstance().putStrategy(_guestRequestFactory, cookieId, strategyId, combineTree,
        HttpStatus.SC_NO_CONTENT);
    
    // GET the newly revised strategy
     strategyResponse = StrategyUtil.getInstance().getStrategy(strategyId, _guestRequestFactory,
        cookieId, HttpStatus.SC_OK);
     revisedStrategy = strategyResponse.as(StrategyResponseBody.class);
    
    // confirm that the response stepTree is the same as what we PUT
    assertEquals(combineTree.toString(), revisedStrategy.getStepTree().toString(),
        "Expected step trees to be equal. Submitted:  " +  combineTree + " received: " + revisedStrategy.getStepTree());

    // DELETE the Strategy
    StrategyUtil.getInstance().deleteStrategy(strategyId, _guestRequestFactory, cookieId,
        HttpStatus.SC_NO_CONTENT);
  }
  
  @Test
  @DisplayName("test that stealing a step from another strategy lands you in jail.") 
  @Tag (Category.PLASMO_TEST)
  void testStolenStep() throws JsonProcessingException {
    
    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
    
    // create a step, and GET its ID
    Response stepResponse = StepUtil.getInstance().createValidStepResponse(_guestRequestFactory, cookieId, ReportUtil.createValidExonCountSearchConfig(), "GenesByExonCount");
    long stepId = stepResponse.body().jsonPath().getLong("id");  

    // post valid strategy with that step id.  now the step belongs to the strategy.
    StrategyUtil.getInstance().createAndPostValidSingleStepStrategy(_guestRequestFactory, cookieId, stepId);

    // try to submit the same step to another strategy.  should fail.
    StrategyUtil.getInstance().createAndPostSingleStepStrategy(_guestRequestFactory, cookieId, stepId, HttpStatus.SC_UNPROCESSABLE_ENTITY);
    
  }
    
    @Test
    @DisplayName("get strategies lists") 
    @Tag (Category.PLASMO_TEST)
    void getStrategiesListing() throws JsonProcessingException {
      
      String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);
      Long strategyId_1 = StrategyUtil.getInstance().createAndPostSingleStepStrategy(_guestRequestFactory, cookieId);
      Long strategyId_2 = StrategyUtil.getInstance().createAndPostSingleStepStrategy(_guestRequestFactory, cookieId);
      
      // GET list of strategies for this user
      Response stratListResponse = StrategyUtil.getInstance().getStrategyList(_guestRequestFactory, cookieId);

      // confirm the list has correct number
      StrategyListItem[] list = stratListResponse.as(StrategyListItem[].class);
      assertEquals(2, list.length, "Expected exactly two strategies, but got " + list.length);
      
      // DELETE the Strategies
      StrategyUtil.getInstance().deleteStrategy(strategyId_1, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);
      StrategyUtil.getInstance().deleteStrategy(strategyId_2, _guestRequestFactory, cookieId, HttpStatus.SC_NO_CONTENT);

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
