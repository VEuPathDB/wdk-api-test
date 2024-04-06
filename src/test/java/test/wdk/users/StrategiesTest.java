package test.wdk.users;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnswerFormatting;
import org.gusdb.wdk.model.api.Strategy;
import org.gusdb.wdk.model.api.StrategyListItem;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.AnswerUtil;
import test.support.util.AuthUtil;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.wdk.StrategyListTest;
import test.wdk.UsersTest;

  @DisplayName("Strategies")
  public class StrategiesTest extends UsersTest {

    public static final String BASE_PATH = UsersTest.USERS_BY_ID_PATH + "/strategies";
    public static final String BY_ID_PATH = BASE_PATH + "/{strategyId}";

    protected final AuthUtil _authUtil;
    private GuestRequestFactory _guestRequestFactory;

    public StrategiesTest(AuthUtil auth, AuthenticatedRequestFactory authReqFactory, GuestRequestFactory guestReqFactory ) {
      super(authReqFactory);
      this._authUtil = auth;
      _guestRequestFactory = guestReqFactory;
    }

    @Test
    @DisplayName("Run public strategy")
    @Disabled("Strategy service is not ready for this test")
    @Tag (Category.PLASMO_TEST)
    void runPublicStrategy() {
      // find a random strategy:  first one in list of public strats
      StrategyListItem[] strategyList =  _guestRequestFactory.jsonSuccessRequest().when().get(StrategyListTest.PUBLIC_STRATS_PATH).as(StrategyListItem[].class);
      String signature = strategyList[0].getSignature();
      signature = "3975e694d0bf0f69"; // TODO: fix this
      Strategy strategy = new Strategy();
      strategy.setSourceSignature(signature);

      // use its signature in request for strategy, which creates a copy of it
      Response response = _guestRequestFactory.jsonPayloadRequest(strategy, HttpStatus.SC_OK, ContentType.JSON)
          .when()
          .post(BASE_PATH, "current");

      long rootStepId = response.body().jsonPath().getLong("latestStepId");

      AnswerFormatting formatting = AnswerUtil.getDefaultFormattingOneRecord();

      _guestRequestFactory.jsonPayloadRequest(formatting, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(StepsTest.BY_ID_PATH + "/answer", "current", rootStepId);
    }

}
