package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("OAuth")
public class OAuthTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/oauth";
  public static final String TOKEN_PATH = BASE_PATH + "/state-token";

  public final Session _session;

  public OAuthTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @Test
  @DisplayName("GET " + TOKEN_PATH)
  void getStateToken() {
    _session.jsonSuccessRequest().when().get(TOKEN_PATH);
  }
}
