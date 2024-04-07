package test.wdk.users;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.AnswerUtil;
import test.support.util.RequestFactory;
import test.support.util.Session;
import test.support.util.SessionFactory;
import test.wdk.UsersTest;

@DisplayName("Steps")
public class StepsTest extends UsersTest {

  public static final String BASE_PATH = UsersTest.USERS_BY_ID_PATH + "/steps";
  public static final String BY_ID_PATH = BASE_PATH + "/{stepId}";

  public StepsTest(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Create and delete a guest step")
  void createAndDeleteGuestStep() {

    Response stepResponse = createExonCountStepResponse(_guestSession);
    long stepId = stepResponse
        .body()
        .jsonPath()
        .getLong("id"); // TODO: use JsonKeys

    // delete the step
    deleteStep(stepId, _guestSession, HttpStatus.SC_NO_CONTENT);

    // deleting again should get a not found
    deleteStep(stepId, _guestSession, HttpStatus.SC_NOT_FOUND);
  }

  public static Response createExonCountStepResponse(Session session) {

    Step step = new Step(AnswerUtil.createExonCountAnswerSpec());
    step.setSearchName("GenesByExonCount");

    return session.jsonPayloadRequest(step, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BASE_PATH, "current");
  }

  private void deleteStep(long stepId, RequestFactory requestFactory, int expectedStatus) {

    requestFactory.emptyRequest()
    .expect()
    .statusCode(expectedStatus)
    .when()
      .delete(BY_ID_PATH, "current", stepId);
  }
}

