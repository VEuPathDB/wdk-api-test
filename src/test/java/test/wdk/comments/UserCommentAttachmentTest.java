package test.wdk.comments;

import io.restassured.http.ContentType;
import org.apidb.apicommon.model.api.PostResponse;
import org.apidb.apicommon.model.api.Target;
import org.apidb.apicommon.model.api.UserCommentPostRequest;
import org.junit.jupiter.api.*;
import test.apicomm.UserCommentTest;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.wdk.TestBase;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;

public class UserCommentAttachmentTest extends TestBase {
  private static final String BASE_PATH = UserCommentTest.ID_PATH + "/attachments";
  private static final String ID_PATH   = BASE_PATH + "/{attachment-id}";

  private static final String TARGET_ID = "PCYB_071350";
  private static final String TARGET_TYPE = "gene";

  protected final AuthenticatedRequestFactory auth;

  protected final GuestRequestFactory guest;

  long commentId;

  public UserCommentAttachmentTest(AuthenticatedRequestFactory auth,
      GuestRequestFactory fac) {
      this.auth = auth;
      this.guest = fac;
  }

  @BeforeEach
  void createComment() {
    commentId = auth.jsonPayloadRequest(
      new UserCommentPostRequest().setHeadline("test")
        .setContent("testing")
        .setTarget(new Target().setId(TARGET_ID).setType(TARGET_TYPE)
      ),
      SC_CREATED,
      ContentType.JSON
    )
      .when()
      .post(UserCommentTest.BASE_PATH)
      .as(PostResponse.class)
      .getId();
  }

  @AfterEach
  void deleteComment() {
    auth.request(SC_NO_CONTENT).when()
      .delete(UserCommentTest.ID_PATH, commentId);
  }

  @Test
  @DisplayName("Attachment")
  void successCondition() {
    PostResponse res = auth.emptyRequest()
        .given()
        .formParam("description", "test")
        .multiPart(
            "file",
            "test.txt",
            "hello it me".getBytes(),
            ContentType.TEXT.toString()
        )
        .expect()
        .statusCode(SC_CREATED)
        .contentType(ContentType.JSON)
        .when()
        .post(BASE_PATH, commentId)
        .as(PostResponse.class);
    auth.emptyRequest()
        .expect()
        .statusCode(SC_NO_CONTENT)
        .when()
        .delete(ID_PATH, res.getId());
  }
}
