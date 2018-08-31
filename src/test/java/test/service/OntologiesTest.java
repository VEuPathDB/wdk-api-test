package test.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static test.support.Conf.SERVICE_PATH;

@DisplayName("Ontologies")
public class OntologiesTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/ontologies";
  public static final String BY_NAME_PATH = BASE_PATH + "/{ontologyName}";

  @Test
  @DisplayName("Get Ontology List")
  void getOntologies() {
    getOntologyList();
  }

  @ParameterizedTest
  @DisplayName("Get Ontology")
  @MethodSource("getOntologyList")
  void getOntology(String name) {
    RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(BY_NAME_PATH, name);
  }

  @ParameterizedTest
  @DisplayName("Post Ontology Path")
  @MethodSource("getOntologyList")
  @Disabled("What is this endpoint?")
  void postOntologyPath(String name) {
    // TODO
  }

  static String[] getOntologyList() {
    return RestAssured.expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
      .when()
        .get(BASE_PATH)
        .body()
        .as(String[].class);
  }
}
