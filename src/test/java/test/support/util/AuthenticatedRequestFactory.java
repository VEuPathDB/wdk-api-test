package test.support.util;

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
    return auth.prepRequest();
  }

  public static AuthenticatedRequestFactory getInstance(AuthUtil auth) {
    if (instance == null) {
      instance = new AuthenticatedRequestFactory(auth);
    }
    return instance;
  }
}
