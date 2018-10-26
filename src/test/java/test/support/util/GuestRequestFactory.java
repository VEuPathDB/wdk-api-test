package test.support.util;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import test.support.Conf;

public class GuestRequestFactory implements RequestFactory {
  private static GuestRequestFactory instance;
  private GuestRequestFactory() {}

  @Override
  public RequestSpecification emptyRequest() {
    RequestSpecification req = RestAssured.given();

    if(Conf.PRINT_REQUESTS)
      req.filter(new RequestLoggingFilter())
          .filter(new ResponseLoggingFilter());

    if(Conf.QA_AUTH != null && !Conf.QA_AUTH.isEmpty()) {
      req.cookie(Conf.QA_AUTH_COOKIE, Conf.QA_AUTH);
    }

    return req;
  }

  public static GuestRequestFactory getInstance() {
    if (instance == null)
      instance = new GuestRequestFactory();
    return instance;
  }
}
