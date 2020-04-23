package test.wdk.users;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnswerFormatting;
import org.gusdb.wdk.model.api.Strategy;
import test.support.util.AnswerUtil;
import test.support.util.RequestFactory;
import test.wdk.UsersTest;

public class StrategyUtil {

  public static final String BASE_PATH = UsersTest.BY_ID_PATH + "/strategies";
  public static final String STRATEGY_ID_PATH = BASE_PATH + "/{id}";

  public static void runStrategyFromSignature(String signature, RequestFactory reqFactory) {

    Strategy strategy = new Strategy();
    strategy.setSourceSignature(signature);
    Response response = reqFactory.jsonPayloadRequest(strategy, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(BASE_PATH, "current");

    long strategyId = response.body().jsonPath().getLong("id");

    response = reqFactory.jsonPayloadRequest(strategy, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .get(STRATEGY_ID_PATH, "current", strategyId);

    long rootStepId = response.body().jsonPath().getLong("latestStepId");

    AnswerFormatting formatting = AnswerUtil.getDefaultFormattingOneRecord();

    reqFactory.jsonPayloadRequest(formatting, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(StepsTest.BY_ID_PATH + "/answer", "current", rootStepId);
  }

}
