package test.wdk.users;

import org.gusdb.wdk.model.api.StrategyListItem;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonProcessingException;

import test.support.Category;
import test.support.util.AuthUtil;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.wdk.StrategyListTest;
import test.wdk.UsersTest;

@Disabled
public class PublicStrategiesTest extends UsersTest {
  public static final String BASE_PATH = UsersTest.BY_ID_PATH + "/strategies";
  public static final String BY_ID_PATH = BASE_PATH + "/{strategyId}";

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
  void runPublicStrategy(StrategyListItem strategyListItem) throws JsonProcessingException {
    
    StrategyUtil.runStrategyFromSignature(strategyListItem.getSignature(), _guestRequestFactory);
  }
  
}
