package test.support.util;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class GuestRequestFactory implements RequestFactory {
  private static GuestRequestFactory instance;
  private GuestRequestFactory() {}

  @Override
  public RequestSpecification emptyRequest() {
    return RestAssured.given()
        .filter(new RequestLoggingFilter())
        .filter(new ResponseLoggingFilter());
  }

  public static GuestRequestFactory getInstance() {
    if (instance == null)
      instance = new GuestRequestFactory();
    return instance;
  }
}
