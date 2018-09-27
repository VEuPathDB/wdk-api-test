package test.service;

import org.gusdb.wdk.model.api.UserIdQueryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.support.util.RequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("User ID Query")
public class UserIdQueryTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/user-id-query";

  private final RequestFactory req;

  UserIdQueryTest(RequestFactory req) {
    this.req = req;
  }

  @ParameterizedTest
  @DisplayName("Get User IDs for email list")
  @MethodSource("buildUserIdSummaryRequests")
  void getUserIds(UserIdQueryRequest body) {
    req.jsonIoSuccessRequest(body).when().post(BASE_PATH);
  }

  private static UserIdQueryRequest[] buildUserIdSummaryRequests() {
    return new UserIdQueryRequest[]{
        // TODO: Find a better way of getting these
      new UserIdQueryRequest("epharper@upenn.edu", "somebody@example.com")
    };
  }
}
