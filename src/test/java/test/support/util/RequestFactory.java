package test.support.util;

import org.apache.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import test.support.Conf;

/**
 * Provides basic RestAssured request creation methods with QA bypass cookie
 * and request/response logging if configured.
 */
public class RequestFactory {

  /**
   * Preps request for WDK test purposes, including:
   *   - Add logging filter
   *   - Add QA auth cookie for QA sites
   *
   * @param request prepared RestAssured object
   * @return request passed in with additional prep
   */
  private RequestSpecification prepRequest(RequestSpecification request) {
    if (Conf.PRINT_REQUESTS) {
      request.filter(new RequestLoggingFilter())
             .filter(new ResponseLoggingFilter());
    }
    if (Conf.QA_AUTH != null && !Conf.QA_AUTH.isEmpty()) {
      request.cookie(Conf.QA_AUTH_COOKIE, Conf.QA_AUTH);
    }
    addAuthorization(request);
    return request;
  }

  protected void addAuthorization(RequestSpecification request) {
    // default implementation adds no authorization tokens
  }

  /**
   * Builds a basic RestAssured request spec with no expectations.
   *
   * @return prepared RestAssured object
   */
  public RequestSpecification emptyRequest() {
    return prepRequest(RestAssured.given());
  }

  /**
   * Builds a request with nothing on it but the expectation of a 200 response
   * code.
   *
   * Suitable for simple get requests.
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification successRequest() {
    return request(HttpStatus.SC_OK);
  }


  /**
   * Builds a request with nothing on it but the expected response content-type
   * with a 200 status code.
   *
   * Suitable for simple get requests.
   *
   * @param contentType expected response content type
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification successRequest(ContentType contentType) {
    return request(HttpStatus.SC_OK, contentType);
  }

  /**
   * Builds a request with nothing on it but an expected response code.
   *
   * Suitable for simple get requests.
   *
   * @param statusCode expected response code
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification request(int statusCode) {
    return emptyRequest().expect().statusCode(statusCode);
  }

  /**
   * Builds a request with nothing on it but the expected response code and
   * response content-type.
   *
   * Suitable for simple get requests.
   *
   * @param statusCode  expected response code
   * @param contentType expected response content type
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification request(int statusCode, ContentType contentType) {
    return request(statusCode).contentType(contentType);
  }

  /**
   * Builds a request with nothing on it but the expected response code and
   * JSON response content-type.
   *
   * Suitable for simple get requests expecting a response in JSON.
   *
   * @param statusCode expected response code
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification jsonRequest(int statusCode) {
    return request(statusCode, ContentType.JSON);
  }

  /**
   * Builds a request with nothing on it but the expectation of a 200 response
   * code and JSON response content-type.
   *
   * Suitable for simple get requests expecting a response in JSON.
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification jsonSuccessRequest()  {
    return jsonRequest(HttpStatus.SC_OK);
  }

  /**
   * Builds a request with a JSON payload which expects the given response code.
   *
   * Suitable for PATCH/POST/PUT requests dealing in JSON
   *
   * @param body       body to send with the request
   * @param statusCode expected response code
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification jsonPayloadRequest(Object body, int statusCode) {
    return emptyRequest()
        .contentType(ContentType.JSON)
        .body(body)
        .expect()
        .statusCode(statusCode);
  }

  /**
   * Builds a request prepared to send a given JSON payload with the expectation
   * that the endpoint will respond with the given status code as well as the
   * given content-type.
   *
   * @param body        outgoing json payload
   * @param statusCode  expected response code
   * @param contentType expected response content-type
   *
   * @return prepared RestAssured object.
   */
  public ResponseSpecification jsonPayloadRequest(
      Object body,
      int statusCode,
      ContentType contentType
  ) {
    return jsonPayloadRequest(body, statusCode)
        .contentType(contentType);
  }

  /**
   * Builds a request with a JSON payload that expects a JSON response.
   *
   * Suitable for PATCH/POST/PUT requests with expected JSON responses.
   *
   * @param body       body to send with the request
   * @param statusCode expected status code
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification jsonIoRequest(Object body, int statusCode) {
    return jsonPayloadRequest(body, statusCode).contentType(ContentType.JSON);
  }

  /**
   * Builds a request with a JSON payload that expects a JSON response with a
   * 200 response code.
   *
   * Suitable for PATCH/POST/PUT requests with expected JSON responses.
   *
   * @param body body to send with the request.
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification jsonIoSuccessRequest(Object body) {
    return jsonIoRequest(body, HttpStatus.SC_OK);
  }
}
