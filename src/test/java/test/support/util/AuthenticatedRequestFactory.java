package test.support.util;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import test.support.Conf;

/**
 * Helper / Factory for building common authenticated requests.
 */
public class AuthenticatedRequestFactory implements RequestFactory {
  private static AuthenticatedRequestFactory instance;
  private final AuthUtil auth;
  private AuthenticatedRequestFactory(AuthUtil auth) {
    this.auth = auth;
  }

  @Override
  public RequestSpecification emptyRequest() {
    RequestSpecification req = auth.prepRequest();

    if(Conf.PRINT_REQUESTS)
      req.filter(new RequestLoggingFilter())
        .filter(new ResponseLoggingFilter());

    if(Conf.QA_AUTH != null && !Conf.QA_AUTH.isEmpty()) {
      req.cookie(Conf.QA_AUTH_COOKIE, Conf.QA_AUTH);
    }

    return req;
  }

  public static AuthenticatedRequestFactory getInstance(AuthUtil auth) {
    if (instance == null) {
      instance = new AuthenticatedRequestFactory(auth);
    }
    return instance;
  }
}
