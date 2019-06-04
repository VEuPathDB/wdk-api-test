package test.wdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import test.support.Conf;
import test.support.ParamResolver;
import util.Json;

@ExtendWith(ParamResolver.class)
public class TestBase {
  private static final ObjectMapper MAPPER = Json.MAPPER;

  @BeforeAll
  public static void baseSetup() {
    RestAssured.config = RestAssured.config().objectMapperConfig(new ObjectMapperConfig()
        .jackson2ObjectMapperFactory((cls, charset) -> MAPPER));
    RestAssured.baseURI = Conf.SITE_PATH;
  }

  protected ObjectMapper getMapper() {
    return MAPPER;
  }
}
