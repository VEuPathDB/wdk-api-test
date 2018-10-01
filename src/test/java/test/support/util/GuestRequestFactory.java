package test.support.util;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class GuestRequestFactory implements RequestFactory {
  private static GuestRequestFactory instance;
  private GuestRequestFactory() {}

  @Override
  public RequestSpecification emptyRequest() {
    return RestAssured.given();
  }

  public static GuestRequestFactory getInstance() {
    if (instance == null)
      instance = new GuestRequestFactory();
    return instance;
  }
}
