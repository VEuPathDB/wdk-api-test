package test.wdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.support.util.GuestRequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("OAuth")
public class OAuthTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/oauth";
  public static final String TOKEN_PATH = BASE_PATH + "/state-token";

  private final GuestRequestFactory req;

  public OAuthTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + TOKEN_PATH)
  void getStateToken() {
    req.jsonSuccessRequest().when().get(TOKEN_PATH);
  }
}
