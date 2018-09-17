package test.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static test.support.Conf.SERVICE_PATH;

@DisplayName("Temporary File")
public class TempFileTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/temporary-file";
  public static final String DELETE_PATH = BASE_PATH + "/{ID}";

  @Test
  @DisplayName("Create and delete temp file")
  void createAndDeleteTempFile() {
      
      Response response =
	  RestAssured
	  .given().multiPart("file", "this is the contents")
	  .expect()
	  .statusCode(HttpStatus.SC_NO_CONTENT)
	  .when()
	  .post(BASE_PATH);
      
      String id = response.getHeader("ID");
      String cookieId = response.getCookie("JSESSIONID");
      
      RestAssured
      .given()
      .cookie("JSESSIONID", cookieId)
      .expect()
      .statusCode(HttpStatus.SC_NO_CONTENT)
      .when().delete(DELETE_PATH, id);
  }

}
