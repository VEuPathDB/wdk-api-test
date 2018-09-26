package test.support.util;

import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

/**
 * Helper / Factory for building common authenticated requests.
 */
public class AuthenticatedRequestFactory {
  private static AuthenticatedRequestFactory instance;
  private final AuthUtil auth;
  private AuthenticatedRequestFactory(AuthUtil auth) {
    this.auth = auth;
  }

  /**
   * Builds an authenticated request with nothing on it but an expected response
   * code.
   *
   * @param retCode expected response code
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification authRequest(int retCode) {
    return auth.prepRequest().expect().statusCode(retCode);
  }

  /**
   * Builds an authenticated request with nothing on it but an expected response
   * code and content type header.
   *
   * @param retCode expected response code
   * @param retType expected response content-type
   *
   * @return prepared RestAssured object.
   */
  public ResponseSpecification authRequest(int retCode, ContentType retType) {
    return authRequest(retCode).contentType(retType);
  }

  /**
   * Builds an authenticated request with nothing on it but an expected response
   * code and an expectation that the response content-type will be JSON.
   *
   * @param retCode expected response code
   *
   * @return prepared RestAssured object.
   */
  public ResponseSpecification authJsonRequest(int retCode) {
    return authRequest(retCode, ContentType.JSON);
  }

  /**
   * Builds an authenticated request prepared to send a given JSON payload with
   * the expectation that the endpoint will respond with the given status code
   * as well as a JSON content-type.
   *
   * @param body    outgoing json payload
   * @param retCode expected response code
   *
   * @return prepared RestAssured object.
   */
  public ResponseSpecification authJsonPayloadRequest(Object body, int retCode) {
    return authJsonPayloadRequest(body, retCode, ContentType.JSON);
  }

  /**
   * Builds an authenticated request prepared to send a given JSON payload with
   * the expectation that the endpoint will respond with the given status code
   * as well as the given content-type.
   *
   * @param body    outgoing json payload
   * @param retCode expected response code
   * @param retType expected response content-type
   *
   * @return prepared RestAssured object.
   */
  public ResponseSpecification authJsonPayloadRequest(Object body, int retCode,
      ContentType retType) {
    return auth.prepRequest()
        .contentType(ContentType.JSON)
        .body(body)
        .expect()
        .statusCode(retCode)
        .contentType(retType);
  }

  public static AuthenticatedRequestFactory getInstance(AuthUtil auth) {
    if (instance == null) {
      instance = new AuthenticatedRequestFactory(auth);
    }
    return instance;
  }
}
