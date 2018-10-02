package test.support.util;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

/**
 * Helper / Factory for building common authenticated requests.
 */
public class AuthenticatedRequestFactory implements RequestFactory {
  private static AuthenticatedRequestFactory instance;
  private final AuthUtil auth;
  private AuthenticatedRequestFactory(AuthUtil auth) {
    this.auth = auth;
  }

  public RequestSpecification emptyRequest() {
    return auth.prepRequest()
        .filter(new RequestLoggingFilter())
        .filter(new ResponseLoggingFilter());
  }

  public static AuthenticatedRequestFactory getInstance(AuthUtil auth) {
    if (instance == null) {
      instance = new AuthenticatedRequestFactory(auth);
    }
    return instance;
  }
}
