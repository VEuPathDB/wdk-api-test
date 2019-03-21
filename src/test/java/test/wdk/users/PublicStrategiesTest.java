package test.wdk.users;

import org.gusdb.wdk.model.api.StrategyListItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import test.support.Category;
import test.support.util.AuthUtil;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.support.util.StrategyUtil;
import test.wdk.StrategyListTest;
import test.wdk.UsersTest;

public class PublicStrategiesTest extends UsersTest {

  protected final AuthUtil _authUtil;
  private GuestRequestFactory _guestRequestFactory;

  public PublicStrategiesTest(AuthUtil auth, AuthenticatedRequestFactory authReqFactory, GuestRequestFactory guestReqFactory ) {
    super(authReqFactory);
    this._authUtil = auth;
    _guestRequestFactory = guestReqFactory;
  }

  @SuppressWarnings("unused")
  private static StrategyListItem[] getPublicStrategies() {
    StrategyListItem[] strategyList = 
        GuestRequestFactory.getInstance().jsonSuccessRequest()
        .when().get(StrategyListTest.PUBLIC_STRATS_PATH)
        .as(StrategyListItem[].class);
    return strategyList;
  }

  @ParameterizedTest
  @DisplayName("Run public strategies")
  @MethodSource("getPublicStrategies")
  @Tag (Category.PUBLIC_STRATEGIES_TEST)
  void runPublicStrategy(StrategyListItem strategyListItem) {
    StrategyUtil.getInstance().runStrategyFromSignature(strategyListItem.getSignature(), _guestRequestFactory);
  }
  
}
