package test.support.util;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.StrategyCopyRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.wdk.users.StepsTest;

public class StrategyUtil {
  private static StrategyUtil instance;

  private StrategyUtil() {}

  public static final String BASE_PATH = UserUtil.BY_ID_PATH + "/strategies";
  public static final String BY_ID_PATH = BASE_PATH + "/{stratId}";

  public static StrategyUtil getInstance() {
    if (instance == null) {
      instance = new StrategyUtil();
    }
    return instance;
  }
  
  public Response getStrategy(long StrategyId, RequestFactory requestFactory, String cookieId, int expectedStatus)
      throws JsonProcessingException {
    return requestFactory.emptyRequest().cookie("JSESSIONID", cookieId).expect().statusCode(
        expectedStatus).when().get(BY_ID_PATH, "current", StrategyId);
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
