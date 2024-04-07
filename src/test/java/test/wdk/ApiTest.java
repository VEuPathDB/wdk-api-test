package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("API")
public class ApiTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/api";

  public final Session _session;

  public ApiTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getApi() {
    _session.successRequest(ContentType.HTML).when().get(BASE_PATH);
  }
}
