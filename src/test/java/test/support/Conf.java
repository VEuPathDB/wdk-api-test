package test.support;

import test.support.util.Auth;

import java.util.Map;
import java.util.Objects;

public class Conf {
  private Conf() {}

  /**
   * Site login authentication type
   */
  public static final Auth.Type AUTH_TYPE;

  /**
   * Site login email address.
   *
   * Set via the environment variable <code>EMAIL</code>.
   */
  public static final String EMAIL;

  /**
   * Site login password
   *
   * Set via the environment variable <code>PASSWORD</code>.
   */
  public static final String PASSWORD;

  /**
   * URL of site under test
   *
   * Set via the environment variable <code>SITE_PATH</code>.
   */
  public static final String SITE_PATH;

  /**
   * Path prefix for REST API service endpoints
   */
  public static final String SERVICE_PATH = "/service";

  /**
   * Name of the WDK Auth token cookie
   */
  public static final String WDK_AUTH_COOKIE = "wdk_check_auth";

  /**
   * Name of the JAX-RS session token cookie
   */
  public static final String JSESS_AUTH_COOKIE = "JSESSIONID";

  /**
   * Name of the WDK Auth token cookie.
   *
   * @deprecated Name is vague as there are multiple auth cookies in play.  Use
   * WDK_AUTH_COOKIE instead.
   */
  @Deprecated
  public static final String AUTH_COOKIE =  WDK_AUTH_COOKIE;

  /**
   * Name of the EuPath auth cookie
   */
  public static final String EUPATH_AUTH_COOKIE = "eupathdb-login";


  /**
   * URL of service providing OAuth endpoints.
   *
   * Overridable using the <code>OAUTH_SERVICE</code> environment variable.
   *
   * Defaults to "https://eupathdb.org/oauth"
   */
  public static final String OAUTH_SERVICE;

  static {
    final Map<String, String> env = System.getenv();

    SITE_PATH = Objects.requireNonNull(env.get("SITE_PATH"));
    EMAIL = Objects.requireNonNull(env.get("EMAIL"));
    PASSWORD = Objects.requireNonNull(env.get("PASSWORD"));
    AUTH_TYPE = Auth.Type.valueOf(env.getOrDefault("AUTH_TYPE", Auth.Type.OAUTH.name()));

    // External service used for OAuth authentication
    OAUTH_SERVICE = env.getOrDefault("OAUTH_SERVICE",
        "https://eupathdb.org/oauth");
  }
}
