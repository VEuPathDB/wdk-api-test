package test.support.util;

import io.restassured.specification.RequestSpecification;

public class GuestRequestFactory implements RequestFactory {

  private static GuestRequestFactory instance;
  private final AuthUtil auth;

  private GuestRequestFactory(AuthUtil auth) {
    this.auth = auth;
  }

  @Override
  public RequestSpecification emptyRequest() {
    return RequestFactory.prepRequest(auth.prepGuestRequest());
  }

  public static GuestRequestFactory getInstance(AuthUtil auth) {
    if (instance == null)
      instance = new GuestRequestFactory(auth);
    return instance;
  }

  public String getGuestAuthToken() {
    return auth.getGuestAuthToken();
  }
}
