package test.support;

import test.support.util.Auth;

import java.util.Map;
import java.util.Objects;

public class Conf {
  private Conf() {}

  public static final Auth.Type AUTH_TYPE;

  public static final String USERNAME;
  public static final String PASSWORD;
  public static final String SITE_PATH;

  public static final String SERVICE_PATH = "/service";

  public static final String AUTH_COOKIE = "wdk_check_auth";

  static {
    final Map<String, String> env = System.getenv();

    SITE_PATH = Objects.requireNonNull(env.get("SITE_PATH"));
    USERNAME = Objects.requireNonNull(env.get("USERNAME"));
    PASSWORD = Objects.requireNonNull(env.get("PASSWORD"));
    AUTH_TYPE = Auth.Type.valueOf(env.getOrDefault("AUTH_TYPE", Auth.Type.OAUTH.name()));
  }
}
