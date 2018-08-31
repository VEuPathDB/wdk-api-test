package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Ontologies")
public class OntologiesTest extends TestBase {
  public static final String ONTOLOGIES_PATH = SERVICE_PATH + "/ontologies";
  public static final String ONTOLOGY_BY_NAME_PATH = ONTOLOGIES_PATH + "/{ontologyName}";

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
        .get(ONTOLOGY_BY_NAME_PATH, name);
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
        .get(ONTOLOGIES_PATH)
        .body()
        .as(String[].class);
  }
}
