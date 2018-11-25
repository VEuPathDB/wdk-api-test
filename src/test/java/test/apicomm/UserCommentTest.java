package test.apicomm;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apidb.apicommon.model.api.PostResponse;
import org.apidb.apicommon.model.api.Target;
import org.apidb.apicommon.model.api.UserCommentPostRequest;
import org.junit.jupiter.api.*;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.wdk.TestBase;

import static org.apache.http.HttpStatus.SC_OK;
import static test.support.Conf.SERVICE_PATH;

public class UserCommentTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/user-comments";
  public static final String ID_PATH   = BASE_PATH + "/{comment-id}";

  private static final String ORGANISM = "Plasmodium cynomolgi";
  private static final String TARGET_ID = "PCYB_071350";
  private static final String TARGET_TYPE = "gene";


  protected final AuthenticatedRequestFactory auth;

  protected final GuestRequestFactory guest;

  public UserCommentTest(AuthenticatedRequestFactory auth,
      GuestRequestFactory fac) {
    this.auth = auth;
    this.guest = fac;
  }

  @Nested
  @DisplayName("Posting a comment")
  public class PostComment {

    @Test
    @DisplayName("should fail when user is not logged in")
    @Disabled
    public void withNoSession() {}

    @Test
    @DisplayName("should fail with 400 when target is missing")
    @Disabled
    public void withNoTarget() {}

    @Test
    @DisplayName("should fail with 400 when headline is missing")
    @Disabled
    public void withNoHeadline() {}

    @Test
    @DisplayName("should fail with 400 when content is missing")
    @Disabled
    public void withNoContent() {}

    @Test
    @DisplayName("should reply with 200 when success")
    public void successCondition() {
      PostResponse res = auth.jsonPayloadRequest(
        new UserCommentPostRequest().setHeadline("test")
          .setContent("testing")
          .setTarget(new Target().setId(TARGET_ID).setType(TARGET_TYPE)
        ),
        HttpStatus.SC_CREATED,
        ContentType.JSON
      )
        .when()
        .post(BASE_PATH)
        .as(PostResponse.class);

      auth.request(HttpStatus.SC_NO_CONTENT).when()
          .delete(ID_PATH, res.getId());
    }
  }

  @Nested
  @DisplayName("Getting a comment")
  public class GetComment {
    long commentId;

    @BeforeEach
    void createComment() {
      commentId = auth.jsonPayloadRequest(
        new UserCommentPostRequest().setHeadline("test")
          .setContent("testing")
          .setTarget(new Target().setId(TARGET_ID).setType(TARGET_TYPE)
        ),
        HttpStatus.SC_CREATED,
        ContentType.JSON
      )
        .when()
        .post(BASE_PATH)
        .as(PostResponse.class)
        .getId();
    }

    @AfterEach
    void deleteComment() {
      auth.request(HttpStatus.SC_NO_CONTENT).when()
          .delete(ID_PATH, commentId);
    }

    @Test
    @DisplayName("should reply with 200 and the comment body on success")
    void successCondition() {
      guest.jsonRequest(SC_OK)
          .when()
          .get(ID_PATH, commentId);
    }
  }

  @Nested
  @DisplayName("Deleting a comment")
  public class DeleteComment {}

  @Nested
  @DisplayName("Listing comments")
  public class ListComments {}
}
