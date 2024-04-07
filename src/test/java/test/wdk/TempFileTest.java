package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("Temporary File")
public class TempFileTest extends TestBase {

  public static final String BASE_PATH = SERVICE_PATH + "/temporary-files";
  public static final String DELETE_PATH = BASE_PATH + "/{ID}";

  private final Session _guestSession1;
  private final Session _guestSession2; 

  TempFileTest(SessionFactory sessionFactory) {
    _guestSession1 = sessionFactory.getCachedGuestSession();
    _guestSession2 = sessionFactory.getNewGuestSession();
  }

  @Test
  @DisplayName("Create and delete temp file")
  void createAndDeleteTempFile() {

    // Test temp file create.  also grab cookie and ID
    Response response = testTempFileCreate();
    String tempFileId = response.getHeader("ID");

    // test DELETE with irrelevant cookie. should get NOT FOUND
    testTempFileDelete(tempFileId, _guestSession2, HttpStatus.SC_NOT_FOUND);

    // test DELETE with silly ID. should get NOT FOUND
    testTempFileDelete("silly-id", _guestSession1, HttpStatus.SC_NOT_FOUND);

    // test real DELETE
    testTempFileDelete(tempFileId, _guestSession1, HttpStatus.SC_NO_CONTENT);
  }

  private Response testTempFileCreate() {
    return _guestSession1.emptyRequest()
        .multiPart("file", "this is the contents")
        .expect()
        .statusCode(HttpStatus.SC_NO_CONTENT)
        .when()
        .post(BASE_PATH);
  }

  private void testTempFileDelete(String fileId, Session session, int expectedStatus) {
    session
      .emptyRequest()
      .expect()
      .statusCode(expectedStatus)
      .when()
      .delete(DELETE_PATH, fileId);
  }

}
