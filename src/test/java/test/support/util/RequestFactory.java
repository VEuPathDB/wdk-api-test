package test.support.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class RequestFactory {
  private static RequestFactory instance;
  private RequestFactory() {}


  /**
   * Builds a request with nothing on it but an expected response code.
   *
   * Suitable for simple get requests.
   *
   * @param statusCode expected response code
   *
   * @return prepared RestAssured object
   */
  public ResponseSpecification request(final int statusCode) {
    return RestAssured.expect().statusCode(statusCode);
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
  public ResponseSpecification request(
      final int statusCode,
      final ContentType contentType
  ) {
    return RestAssured.expect().statusCode(statusCode).contentType(contentType);
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
  public ResponseSpecification jsonRequest(final int statusCode) {
    return request(statusCode, ContentType.JSON);
  }

  public ResponseSpecification jsonSuccessRequest() {
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
  public ResponseSpecification jsonPayloadRequest(
      final Object body,
      final int statusCode
  ) {
    return RestAssured.given().contentType(ContentType.JSON).body(body)
        .expect().statusCode(statusCode);
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
  public ResponseSpecification jsonIoRequest(
      final Object body,
      final int statusCode
  ) {
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
  public ResponseSpecification jsonIoSuccessRequest(final Object body) {
    return jsonIoRequest(body, HttpStatus.SC_OK);
  }

  public static RequestFactory getInstance() {
    if (instance == null)
      instance = new RequestFactory();
    return instance;
  }
}
