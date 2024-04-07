package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.gusdb.wdk.model.api.StrategyListItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("Public Strategy List")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StrategyListTest extends TestBase {

  public static final String BASE_PATH = SERVICE_PATH + "/strategy-lists";
  public static final String PUBLIC_STRATS_PATH = BASE_PATH + "/public";

  public final Session _session;

  public StrategyListTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @Test
  @DisplayName("GET " + PUBLIC_STRATS_PATH)
  void getPublicStrategies() {
    getPublicStrategiesList();
  }

  StrategyListItem[] getPublicStrategiesList() {
    return _session.jsonSuccessRequest().when().get(PUBLIC_STRATS_PATH).as(StrategyListItem[].class);
  }
}
