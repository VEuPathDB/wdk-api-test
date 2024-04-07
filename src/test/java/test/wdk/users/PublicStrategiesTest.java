package test.wdk.users;

import org.gusdb.wdk.model.api.StrategyListItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import test.support.Category;
import test.support.util.SessionFactory;
import test.wdk.StrategyListTest;
import test.wdk.UsersTest;

public class PublicStrategiesTest extends UsersTest {

  public static final String BASE_PATH = UsersTest.USERS_BY_ID_PATH + "/strategies";
  public static final String BY_ID_PATH = BASE_PATH + "/{strategyId}";

  public PublicStrategiesTest(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @SuppressWarnings("unused")
  private StrategyListItem[] getPublicStrategies() {
    return _guestSession
      .jsonSuccessRequest()
      .when()
      .get(StrategyListTest.PUBLIC_STRATS_PATH)
      .as(StrategyListItem[].class);
  }

  @ParameterizedTest
  @DisplayName("Run public strategies")
  @MethodSource("getPublicStrategies")
  @Tag (Category.PUBLIC_STRATEGIES_TEST)
  void runPublicStrategy(StrategyListItem strategyListItem) {
    StrategyUtil.runStrategyFromSignature(strategyListItem.getSignature(), _guestSession);
  }

}
