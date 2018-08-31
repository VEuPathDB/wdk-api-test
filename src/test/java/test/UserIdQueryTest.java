package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.gusdb.wdk.model.api.UserIdQueryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("User ID Query")
public class UserIdQueryTest extends TestBase {
  public static final String USER_ID_QUERY_PATH = SERVICE_PATH + "/user-id-query";

  @ParameterizedTest
  @DisplayName("Get User IDs for email list")
  @MethodSource("buildUserIdSummaryRequests")
  void getUserIds(UserIdQueryRequest req) {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(req)
      .expect()
        .contentType(ContentType.JSON)
      .when()
        .post(USER_ID_QUERY_PATH);
  }

  private static UserIdQueryRequest[] buildUserIdSummaryRequests() {
    return new UserIdQueryRequest[]{
        // TODO: Find a better way of getting these
      new UserIdQueryRequest("epharper@upenn.edu", "somebody@example.com")
    };
  }
}
