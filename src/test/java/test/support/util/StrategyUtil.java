package test.support.util;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.StepTreeNode;
import org.gusdb.wdk.model.api.StrategyCopyRequest;
import org.gusdb.wdk.model.api.StrategyCreationRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.wdk.users.StepsTest;

public class StrategyUtil {
  private static StrategyUtil instance;

  private StrategyUtil() {}

  public static final String BASE_PATH = UserUtil.BY_ID_PATH + "/strategies";
  public static final String BY_ID_PATH = BASE_PATH + "/{stratId}";
  public static final String BY_ID_PATH_WITH_STEP_TREE = BY_ID_PATH + "/step-tree";

  public static StrategyUtil getInstance() {
    if (instance == null) {
      instance = new StrategyUtil();
    }
    return instance;
  }
  
  public long createAndPostSingleStepStrategy(RequestFactory requestFactory, String cookieId) throws JsonProcessingException {

    // create step, and get its ID
    SearchConfig exonCountSrchConfig = ReportUtil.createValidExonCountSearchConfig();
    Response stepResponse = StepUtil.getInstance().createValidStepResponse(requestFactory, cookieId, exonCountSrchConfig, "GenesByExonCount");
    long stepId = stepResponse.body().jsonPath().getLong("id"); 

    // create and post the strat
    return createAndPostValidSingleStepStrategy(requestFactory, cookieId, stepId);
  }
  
  public long createAndPostValidSingleStepStrategy(RequestFactory requestFactory, String cookieId, long stepId) throws JsonProcessingException {
    Response response = createAndPostSingleStepStrategy(requestFactory, cookieId, stepId, HttpStatus.SC_OK, ContentType.JSON);
    return response.body().jsonPath().getLong("id"); 
  }
  
  public Response createAndPostSingleStepStrategy(RequestFactory requestFactory, String cookieId, long stepId, int expectedStatusCode, ContentType expectedContentType) throws JsonProcessingException {

    // create simple step tree using provided stepId
    StepTreeNode stepTree = new StepTreeNode(stepId);

    // POST the strat, and extract its ID from the response
    StrategyCreationRequest strat = new StrategyCreationRequest("my strat", stepTree);
    return requestFactory.jsonPayloadRequest(strat, expectedStatusCode,
        expectedContentType).request().cookie("JSESSIONID", cookieId).when().post(BASE_PATH, "current");
  }

  public void putStrategy(RequestFactory requestFactory, String cookieId, Long strategyId, StepTreeNode stepTree, int expectedStatus) throws JsonProcessingException {

    requestFactory.jsonPayloadRequest(stepTree, HttpStatus.SC_OK,
        ContentType.JSON).request().cookie("JSESSIONID", cookieId).when().put(BY_ID_PATH_WITH_STEP_TREE, "current", strategyId);
  }
  
  public Response getStrategy(long StrategyId, RequestFactory requestFactory, String cookieId, int expectedStatus)
      throws JsonProcessingException {
    return requestFactory.emptyRequest().cookie("JSESSIONID", cookieId).expect().statusCode(
        expectedStatus).when().get(BY_ID_PATH, "current", StrategyId);
  }

  public Response getStrategyList(RequestFactory requestFactory, String cookieId)
      throws JsonProcessingException {
    return requestFactory.emptyRequest().cookie("JSESSIONID", cookieId).expect().statusCode(
        HttpStatus.SC_OK).when().get(BASE_PATH, "current");
  }
  
  public void deleteStrategy(long StrategyId, RequestFactory requestFactory, String cookieId, int expectedStatus)
      throws JsonProcessingException {

    requestFactory.emptyRequest().cookie("JSESSIONID", cookieId).expect().statusCode(
        expectedStatus).when().delete(BY_ID_PATH, "current", StrategyId);
  }  

  public void runStrategyFromSignature(String signature, RequestFactory reqFactory) {
    
    StrategyCopyRequest strategy = new StrategyCopyRequest();
    strategy.setSourceSignature(signature);
    Response response = reqFactory.jsonPayloadRequest(strategy, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(BASE_PATH, "current");

    long strategyId = response.body().jsonPath().getLong("id");

    response = reqFactory.jsonPayloadRequest(strategy, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .get(BY_ID_PATH, "current", strategyId);        
    
    long rootStepId = response.body().jsonPath().getLong("latestStepId");

    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();

    reqFactory.jsonPayloadRequest(reportConfig, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(StepsTest.BY_ID_PATH + "/reports/standard", "current", rootStepId);    
  }

}
