package test.wdk;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import test.support.Conf;
import test.support.util.AuthUtil;
import test.support.util.GuestRequestFactory;
import test.support.util.RequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Temporary File")
public class TempFileTest extends TestBase {

  public static final String BASE_PATH = SERVICE_PATH + "/temporary-files";
  public static final String DELETE_PATH = BASE_PATH + "/{ID}";

  private final GuestRequestFactory req;

  TempFileTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("Create and delete temp file")
  void createAndDeleteTempFile() {

    // Test temp file create.  also grab cookie and ID
    Response response = testTempFileCreate();
    String tempFileId = response.getHeader("ID");
    String authCookie = req.getGuestAuthToken();

    // test DELETE with irrelevant cookie. should get NOT FOUND
    testTempFileDelete(tempFileId, AuthUtil.createGuestToken(), HttpStatus.SC_NOT_FOUND);

    // test DELETE with silly ID. should get NOT FOUND
    testTempFileDelete("silly-id", authCookie, HttpStatus.SC_NOT_FOUND);

    // test real DELETE
    testTempFileDelete(tempFileId, authCookie, HttpStatus.SC_NO_CONTENT);
  }

  private Response testTempFileCreate() {
    return req.emptyRequest()
        .multiPart("file", "this is the contents")
        .expect()
        .statusCode(HttpStatus.SC_NO_CONTENT)
        .when()
        .post(BASE_PATH);
  }

  private void testTempFileDelete(String fileId, String authCookie, int expectedStatus) {
    RequestFactory.prepRequest(RestAssured.given()
        .cookie(Conf.WDK_AUTH_COOKIE, authCookie))
        .expect()
        .statusCode(expectedStatus)
        .when()
        .delete(DELETE_PATH, fileId);
  }

}
