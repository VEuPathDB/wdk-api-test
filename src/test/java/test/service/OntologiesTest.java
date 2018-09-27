package test.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import test.support.util.RequestFactory;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Ontologies")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OntologiesTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/ontologies";
  public static final String BY_NAME_PATH = BASE_PATH + "/{ontologyName}";

  public final RequestFactory req;

  public OntologiesTest(RequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("Get Ontology List")
  void getOntologies() {
    getOntologyList();
  }

  @ParameterizedTest
  @DisplayName("Get Ontology")
  @MethodSource("getOntologyList")
  void getOntology(String name) {
    req.jsonSuccessRequest().when().get(BY_NAME_PATH, name);
  }

  @ParameterizedTest
  @DisplayName("Post Ontology Path")
  @MethodSource("getOntologyList")
  @Disabled("What is this endpoint?")
  void postOntologyPath(String name) {
    // TODO
  }

  String[] getOntologyList() {
    return req.jsonSuccessRequest().when().get(BASE_PATH).as(String[].class);
  }
}
