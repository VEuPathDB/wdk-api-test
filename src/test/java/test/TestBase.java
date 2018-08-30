package test;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;
import java.util.Objects;

public class TestBase {
  public static final String SERVICE_PATH = "/service";

  protected static String username;

  protected static String password;

  @BeforeAll
  public static void baseSetup() {
    final Map<String, String> env = System.getenv();

    RestAssured.baseURI = Objects.requireNonNull(env.get("BASE_URI"));
    username = Objects.requireNonNull(env.get("USERNAME"));
    password = Objects.requireNonNull(env.get("PASSWORD"));
  }
}
