package test.support.util;

import io.restassured.specification.RequestSpecification;
import test.support.Conf;

/**
 * Extension of RequestFactory that addsnauthentication modifications
 * to basic RestAssured requests provided by RequestFactory
 */
public class Session extends RequestFactory {

  private final String _bearerToken;

  public Session(String bearerToken) {
    _bearerToken = bearerToken;
  }

  @Override
  protected void addAuthorization(RequestSpecification request) {
    request.cookie(Conf.WDK_AUTH_COOKIE, _bearerToken);
  }

}
