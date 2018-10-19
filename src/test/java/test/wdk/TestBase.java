package test.wdk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import test.support.Conf;
import test.support.ParamResolver;

@ExtendWith(ParamResolver.class)
public class TestBase {
  private static final ObjectMapper MAPPER = new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

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
