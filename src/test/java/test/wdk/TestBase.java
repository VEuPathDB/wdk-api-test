package test.wdk;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import test.support.Conf;
import test.support.ParamResolver;

@ExtendWith(ParamResolver.class)
public class TestBase {
  @BeforeAll
  public static void baseSetup() {
    RestAssured.baseURI = Conf.SITE_PATH;
  }
}
