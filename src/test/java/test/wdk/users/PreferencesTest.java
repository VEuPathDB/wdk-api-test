package test.wdk.users;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.UserPreferencePatchRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import test.support.util.AuthUtil;
import test.support.util.AuthenticatedRequestFactory;
import test.support.util.GuestRequestFactory;
import test.support.util.UserUtil;
import test.wdk.UsersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Map;

public class PreferencesTest extends UsersTest {
  
  protected final AuthUtil _authUtil;
  private GuestRequestFactory _guestRequestFactory;
  
  public static final String BASE_PATH = UsersTest.BY_ID_PATH + "/preferences";
  public static final String BY_PROJECT_PATH = BASE_PATH + "/{project}";


  public PreferencesTest(
    AuthUtil auth,
    AuthenticatedRequestFactory authReqFactory,
    GuestRequestFactory guestReqFactory
  ) {
    super(authReqFactory);
    this._authUtil = auth;
    _guestRequestFactory = guestReqFactory;
  }
  
  @Test
  @DisplayName("Patch and get preferences")
  void patchAndGetUserPreferences() {
    String cookieId = UserUtil.getInstance().getNewCookieId(_guestRequestFactory);

    // stuff a global preference into the database
    UserPreferencePatchRequest patch = new UserPreferencePatchRequest("UPDATE");
    
    patch.getUpdates().put("happy", "yes");
    _guestRequestFactory.jsonPayloadRequest(patch, HttpStatus.SC_NO_CONTENT).
      request().cookie("JSESSIONID", cookieId).when().patch(BY_PROJECT_PATH, "current", "global");

    // see if we get it back.
    Response prefResponse = _guestRequestFactory.jsonSuccessRequest().request().cookie("JSESSIONID", cookieId).when().get(BASE_PATH, "current");
    
    Map<String, String> globalMap = prefResponse
        .body()
        .jsonPath()
        .getMap("global"); 

    assertNotEquals(null, globalMap.get("happy"),  "Expected preference 'happy'");

    assertEquals("yes", globalMap.get("happy"),  "Expected preference 'happy' to be value 'yes'");
   
  }






}
