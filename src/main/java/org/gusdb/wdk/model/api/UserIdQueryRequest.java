package org.gusdb.wdk.model.api;

import java.util.Arrays;

public class UserIdQueryRequest {
  private final String[] emails;

  public UserIdQueryRequest(String... emails) {
    this.emails = emails;
  }

  public String[] getEmails() {
    return emails;
  }

  @Override
  public String toString() {
    return "UserIdQueryRequest{" + "emails=" + Arrays.toString(emails) + '}';
  }
}
