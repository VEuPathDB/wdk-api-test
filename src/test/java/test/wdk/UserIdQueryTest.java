package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.gusdb.wdk.model.api.UserIdQueryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("User ID Query")
public class UserIdQueryTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/user-id-query";

  public final Session _session;

  UserIdQueryTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @ParameterizedTest(name = "POST " + BASE_PATH)
  @DisplayName("POST " + BASE_PATH)
  @MethodSource("buildUserIdSummaryRequests")
  void getUserIds(UserIdQueryRequest body) {
    _session.jsonIoSuccessRequest(body).when().post(BASE_PATH);
  }

  private static UserIdQueryRequest[] buildUserIdSummaryRequests() {
    return new UserIdQueryRequest[]{
        // TODO: Find a better way of getting these
      new UserIdQueryRequest("epharper@upenn.edu", "somebody@example.com")
    };
  }
}
