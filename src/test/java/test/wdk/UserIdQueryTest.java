package test.wdk;

import org.gusdb.wdk.model.api.UserIdQueryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import test.support.util.GuestRequestFactory;

@DisplayName("User ID Query")
public class UserIdQueryTest extends TestBase {
  public static final String BASE_PATH = "/user-id-query";

  private final GuestRequestFactory req;

  UserIdQueryTest(GuestRequestFactory req) {
    this.req = req;
  }

  @ParameterizedTest(name = "POST " + BASE_PATH)
  @DisplayName("POST " + BASE_PATH)
  @MethodSource("buildUserIdSummaryRequests")
  void getUserIds(UserIdQueryRequest body) {
    req.jsonIoSuccessRequest(body).when().post(BASE_PATH);
  }

  public static UserIdQueryRequest[] buildUserIdSummaryRequests() {
    return new UserIdQueryRequest[]{
      // TODO: Find a better way of getting these
      new UserIdQueryRequest("epharper@upenn.edu", "somebody@example.com")
    };
  }
}
