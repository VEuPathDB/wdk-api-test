package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import test.support.util.GuestRequestFactory;

@DisplayName("Ontologies")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OntologiesTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/ontologies";
  public static final String BY_NAME_PATH = BASE_PATH + "/{ontologyName}";

  public final GuestRequestFactory req;

  public OntologiesTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getOntologies() {
    getOntologyList();
  }

  @ParameterizedTest(name = "GET " + BASE_PATH + "/{arguments}")
  @DisplayName("GET " + BY_NAME_PATH)
  @MethodSource("getOntologyList")
  void getOntology(String name) {
    req.jsonSuccessRequest().when().get(BY_NAME_PATH, name);
  }

  String[] getOntologyList() {
    return req.jsonSuccessRequest().when().get(BASE_PATH).as(String[].class);
  }
}
