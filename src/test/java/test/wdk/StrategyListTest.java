package test.wdk;

import org.gusdb.wdk.model.api.StrategyListItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import test.support.util.GuestRequestFactory;

@DisplayName("Public Strategy List")
public class StrategyListTest extends TestBase {
  public static final String BASE_PATH = "/strategy-lists";
  public static final String PUBLIC_STRATS_PATH = BASE_PATH + "/public";


  public final GuestRequestFactory guestRequestFactory;

  public StrategyListTest(GuestRequestFactory req) {
    this.guestRequestFactory = req;
  }

  @Test
  @DisplayName("GET " + PUBLIC_STRATS_PATH)
  void getPublicStrategies() {
    getPublicStrategiesList();
  }

  StrategyListItem[] getPublicStrategiesList() {
    return guestRequestFactory.jsonSuccessRequest().when().get(PUBLIC_STRATS_PATH).as(StrategyListItem[].class);
  }
}
