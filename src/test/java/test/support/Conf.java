package test.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import test.support.util.SessionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Conf {
  private Conf() {}

  /**
   * Site login authentication type
   */
  public static final SessionFactory.Type AUTH_TYPE;

  /**
   * Site login credential sets
   */
  public static final Credentials[] CREDENTIALS;

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
  public static final String WDK_AUTH_COOKIE = "Authorization";

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

  public static final String QA_AUTH_COOKIE = "auth_tkt";

  /**
   * URL of service providing OAuth endpoints.
   *
   * Overridable using the <code>OAUTH_SERVICE</code> environment variable.
   *
   * Defaults to "https://eupathdb.org/oauth"
   */
  public static final String OAUTH_SERVICE;

  public static final String QA_AUTH;

  public static final boolean PRINT_REQUESTS;

  static {
    final Map<String, String> env = System.getenv();
    final ObjectMapper json = new ObjectMapper();

    String sitePath = Objects.requireNonNull(env.get("SITE_PATH"));
    SITE_PATH = sitePath.endsWith("/")
        ? sitePath.substring(0, sitePath.length() - 1)
        : sitePath;

    PRINT_REQUESTS="true".equals(env.get("PRINT_HTTP"));

    AUTH_TYPE = SessionFactory.Type.valueOf(env.getOrDefault("AUTH_TYPE", SessionFactory.Type.OAUTH.name()));
    QA_AUTH = env.get("QA_AUTH");

    // External service used for OAuth authentication
    OAUTH_SERVICE = env.getOrDefault("OAUTH_SERVICE",
        "https://eupathdb.org/oauth");

    // Parse credentials from the command line
    try {
      CREDENTIALS = json.readerFor(Credentials[].class)
          .readValue(env.getOrDefault("CREDENTIALS","[]"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
